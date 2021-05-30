package com.example.runningapp.presentation.authorization

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.runningapp.ApplicationDelegate
import com.example.runningapp.R
import com.example.runningapp.databinding.ActivityAuthBinding
import com.example.runningapp.presentation.common.RunActivity
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    @Inject
    lateinit var viewModel: AuthorizationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationDelegate.getScreenComponent().inject(this)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLiveDataListeners()
    }

    private fun initLiveDataListeners() {
        viewModel.isUserAuthenticated().observe(this) {
            if (it) {
                onAuthenticated()
            }
            else{
                navigateToAuthentication()
            }
        }
    }

    private fun navigateToAuthentication() {
         findNavController(R.id.hostAuthFragment).navigate(R.id.authenticationFragment)
    }

    private fun onAuthenticated() {
        val intent = Intent(this, RunActivity::class.java)
        startActivity(intent)
        finish()
    }
}