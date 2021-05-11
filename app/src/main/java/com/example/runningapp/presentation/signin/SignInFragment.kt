package com.example.runningapp.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.runningapp.R
import com.example.runningapp.databinding.FragmentSignInBinding
import com.example.runningapp.presentation.common.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var userViewModel: SignUpViewModel

    private var _binding: FragmentSignInBinding? = null
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
            btnSignIn.setOnClickListener {
                val user = userViewModel.signIn(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }
        }
    }

    private fun initLiveDataListeners() {
        userViewModel.getCurrentUser().observe(viewLifecycleOwner) {
            it?.let {
                navigateToProfile()
            }
        }
        userViewModel.getSignInErrorLiveData().observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToProfile() {
        findNavController().navigate(R.id.action_signUpFragment_to_profileFragment2)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}