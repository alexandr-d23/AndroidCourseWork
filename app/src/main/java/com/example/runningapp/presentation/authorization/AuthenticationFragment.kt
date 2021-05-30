package com.example.runningapp.presentation.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.runningapp.R
import com.example.runningapp.databinding.FragmentAuthenticationBinding

class AuthenticationFragment : Fragment() {

    private var _binding: FragmentAuthenticationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthenticationBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initListeners()
    }

    private fun initListeners(){
        with(binding){
            val navController = findNavController()
            btnLogIn.setOnClickListener {
                navController.navigate(R.id.action_authenticationFragment_to_signInFragment)
            }
            btnSignUp.setOnClickListener {
                navController.navigate(R.id.action_authenticationFragment_to_signUpFragment)
            }
        }
    }
}