package com.example.runningapp.presentation.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.runningapp.domain.model.User
import com.example.runningapp.domain.usecases.UserUseCase

class UserDetailsViewModel(
    private val userUseCase: UserUseCase,
) : ViewModel() {

    fun getUserById(userId: String): LiveData<User> = userUseCase.getUserById(userId)

}