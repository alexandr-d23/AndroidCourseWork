package com.example.runningapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.runningapp.domain.usecases.UserUseCase
import com.example.runningapp.presentation.authentication.SignUpViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
   private val userUseCase: UserUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(userUseCase) as? T
                    ?: throw IllegalArgumentException("Unknown viewmodel class")
            }
            else -> {
                throw IllegalArgumentException("Unknown viewmodel class")
            }
        }

}