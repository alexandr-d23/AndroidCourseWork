package com.example.runningapp.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.runningapp.R
import com.example.runningapp.data.room.entities.User
import com.example.runningapp.databinding.FragmentProfileBinding
import com.example.runningapp.presentation.common.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var userViewModel: SignUpViewModel

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(
            viewModelStore,
            viewModelFactory
        ).get(SignUpViewModel::class.java)
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
            it?.let {
                bindUser(it)
            } ?: run {
                navigateToAuthentication()
            }
        }
    }

    private fun bindUser(user: User) {
        with(binding) {
            etEmail.setText(user.email)
            etName.setText(user.name)
        }
    }

    private fun navigateToAuthentication() {
        findNavController().navigate(R.id.action_profileFragment_to_authenticationFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}