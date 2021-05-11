package com.example.runningapp.presentation.common

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.runningapp.R
import com.example.runningapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigateToRunning()
        findNavController(R.id.hostFragment).addOnDestinationChangedListener { _, destination, _->
            when(destination.id){ }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToRunning()
    }

    override fun onStart() {
        super.onStart()
        setListeners()
    }

    private fun navigateToRunning(){
        findNavController(R.id.hostFragment).navigate(R.id.runFragment)
    }

    private fun setListeners() {
        with(binding) {
            bnvMenu.setupWithNavController(findNavController(R.id.hostFragment))
            bnvMenu.setOnNavigationItemReselectedListener {}
        }
    }

}