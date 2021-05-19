package com.example.runningapp.presentation.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.runningapp.ApplicationDelegate
import com.example.runningapp.databinding.FragmentProfileBinding
import com.example.runningapp.databinding.FragmentUserDetailsBinding
import com.example.runningapp.domain.model.User
import com.example.runningapp.presentation.common.HistoryAdapter
import com.example.runningapp.presentation.common.ViewModelFactory
import javax.inject.Inject

class UserDetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var userViewModel: UserDetailsViewModel

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationDelegate.component.inject(this)
        userViewModel = ViewModelProvider(
            viewModelStore,
            viewModelFactory
        ).get(UserDetailsViewModel::class.java)
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
        arguments?.let {
            val id = UserDetailsFragmentArgs.fromBundle(it).userId
            userViewModel.getUserById(id).observe(viewLifecycleOwner) { user ->
                bindUser(user)
                adapter.submitList(user.sprints)
            }
        } ?: run {
            throw IllegalStateException("user id not found")
        }
    }

    private fun bindUser(user: User) {
        with(binding) {
            etEmail.setText(user.email)
            etName.setText(user.name)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}