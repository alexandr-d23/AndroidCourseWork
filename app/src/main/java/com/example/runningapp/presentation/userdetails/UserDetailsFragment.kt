package com.example.runningapp.presentation.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.runningapp.ApplicationDelegate
import com.example.runningapp.databinding.FragmentUserDetailsBinding
import com.example.runningapp.domain.model.User
import com.example.runningapp.presentation.common.HistoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDetailsFragment : Fragment() {
    @Inject
    lateinit var userViewModel: UserDetailsViewModel

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationDelegate.getScreenComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailsBinding.inflate(layoutInflater, container, false)
        initializeRecycler()
        return binding.root
    }

    private fun initializeRecycler() {
        binding.rvSprints.let { rv ->
            adapter = HistoryAdapter().also {
                rv.adapter = it
            }
            rv.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()
        initListeners()
        initLiveDataListeners()
    }

    private fun initListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initLiveDataListeners() {
        arguments?.let { bundle ->
            val id = UserDetailsFragmentArgs.fromBundle(bundle).userId
            userViewModel.getUserById(id).observe(viewLifecycleOwner) { user ->
                user?.let {
                    bindUser(user)
                    adapter.submitList(user.sprints)
                }
            }
            lifecycleScope.launch(Dispatchers.IO){
                val sprints = userViewModel.getSprints(id)
                withContext(Dispatchers.Main){
                    sprints.observe(viewLifecycleOwner){
                        adapter.submitList(it)
                    }
                }
            }
        } ?: run {
            throw IllegalStateException("user id not found")
        }
    }

    private fun bindUser(user: User) {
        with(binding) {
            etEmail.text = user.email
            etName.text = user.name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}