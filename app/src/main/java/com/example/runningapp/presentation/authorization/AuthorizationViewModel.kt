package com.example.runningapp.presentation.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.runningapp.domain.usecases.UserUseCase

class AuthorizationViewModel(
    private val userUseCase: UserUseCase
) : ViewModel() {

    fun isUserAuthenticated(): LiveData<Boolean> = userUseCase.isUserAuthenticated()

}