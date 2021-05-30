package com.example.runningapp.presentation.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.runningapp.ApplicationDelegate
import com.example.runningapp.databinding.FragmentProfileBinding
import com.example.runningapp.domain.model.User
import com.example.runningapp.presentation.common.HistoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileFragment : Fragment() {

    @Inject
    lateinit var userViewModel: ProfileViewModel

    private var _binding: FragmentProfileBinding? = null
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
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
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
        with(binding) {
            btnSignOut.setOnClickListener {
                userViewModel.signOut()
            }
            btnUpdate.setOnClickListener {
                userViewModel.updateName(binding.etName.text.toString())
            }
        }
    }

    private fun initLiveDataListeners() {
        userViewModel.getCurrentUser().observe(viewLifecycleOwner) {
            bindUser(it ?: throw IllegalStateException("User not found"))
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val sprints = userViewModel.getSprints()
            withContext(Dispatchers.Main) {
                sprints.observe(viewLifecycleOwner) {
                    Log.d("MYTAG", "HistoryFragment initLiveDataListeners ${it.size}")
                    adapter.submitList(it)
                }
            }

        }

        userViewModel.getError().observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            Log.d("MYTAG", "HistoryFragment: initLiveDataListeners: HISTORY ERROR ${it.message}")
        }
    }

    private fun bindUser(user: User) {
        with(binding) {
            Log.d("MYTAG", "email ${user.email} name ${user.name}")
            etEmail.setText(user.email)
            etName.setText(user.name)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}