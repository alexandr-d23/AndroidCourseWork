package com.example.runningapp.presentation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.runningapp.ApplicationDelegate
import com.example.runningapp.databinding.FragmentSignUpBinding
import javax.inject.Inject

class SignUpFragment : Fragment() {
    @Inject
    lateinit var userViewModel: SignUpViewModel

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        ApplicationDelegate.getScreenComponent().inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        initListeners()
        initLiveDataListeners()
    }

    private fun initListeners() {
        with(binding) {
            btnSignUp.setOnClickListener {
                userViewModel.signUp(
                    etEmail.text.toString(),
                    etName.text.toString(),
                    etPassword.text.toString()
                )
            }
        }
    }

    private fun initLiveDataListeners() {
        userViewModel.getSignUpErrorLiveData().observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}