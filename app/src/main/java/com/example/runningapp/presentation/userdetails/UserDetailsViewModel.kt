package com.example.runningapp.presentation.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.runningapp.domain.model.Sprint
import com.example.runningapp.domain.model.User
import com.example.runningapp.domain.usecases.RunUseCase
import com.example.runningapp.domain.usecases.UserUseCase
import javax.inject.Inject

class UserDetailsViewModel  @Inject constructor(
    private val userUseCase: UserUseCase,
    private val runUseCase: RunUseCase
) : ViewModel() {

    fun getUserById(userId: String): LiveData<User?> = userUseCase.getUserById(userId)

    suspend fun getSprints(userId: String): LiveData<List<Sprint>> = runUseCase.getSprints(userId)

}