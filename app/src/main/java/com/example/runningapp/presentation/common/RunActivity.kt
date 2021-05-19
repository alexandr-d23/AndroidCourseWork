package com.example.runningapp.presentation.common

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.runningapp.ApplicationDelegate
import com.example.runningapp.R
import com.example.runningapp.databinding.ActivityMainBinding
import com.example.runningapp.presentation.authorization.AuthActivity
import com.example.runningapp.presentation.authorization.AuthorizationViewModel
import javax.inject.Inject

class RunActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: AuthorizationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ApplicationDelegate.component.inject(this)
        findNavController(R.id.hostFragment).addOnDestinationChangedListener { _, destination, _->
            when(destination.id){

            }
        }
        viewModel = ViewModelProvider(
            viewModelStore,
            viewModelFactory
        ).get(AuthorizationViewModel::class.java)
        initLiveDataListeners()
    }

    private fun initLiveDataListeners() {
        viewModel.isUserAuthenticated().observe(this) {
            if (!it) {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController(R.id.hostFragment).navigateUp()
    }

    override fun onStart() {
        super.onStart()
        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            bnvMenu.setupWithNavController(findNavController(R.id.hostFragment))
            bnvMenu.setOnNavigationItemReselectedListener {}
        }
    }

}