package com.example.runningapp.domain.usecases

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.runningapp.data.network.model.Subscription
import com.example.runningapp.domain.model.User
import com.example.runningapp.domain.repositories.AuthRepository
import com.example.runningapp.domain.repositories.UserRepository

class UserUseCase(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
){

    suspend fun updateUsername(name: String) {
        authRepository.updateUsername(name)
        authRepository.getCurrentUser()?.let {
            updateUser(it.also {
                it.name = name
            })
        } ?: throw IllegalStateException("Not authorized")
    }

    suspend fun signUp(user: User): User? {
        val userRes = authRepository.signUp(user)
        Log.d("MYTAG", "UserUseCaseImpl sighUp(): ${userRes}")
        userRes?.let {
            addUser(it)
        }
        return userRes
    }

    suspend fun signIn(user: User): User? = authRepository.signIn(user)

    fun getCurrentUser(): User? {
        return authRepository.getCurrentUser()
    }

    suspend fun signOut() = authRepository.signOut()

    suspend fun subscribe(subscribedId: String) {
        Log.d("MYTAG", "UserUseCaseImpl subscribe() : ${subscribedId}")
        userRepository.subscribe(
            Subscription(
                getUserId(),
                subscribedId
            )
        )
    }

    suspend fun unsubscribe(subscribedId: String) {
        Log.d("MYTAG", "UserUseCaseImpl unsubscribe() : ${subscribedId}")
        userRepository.unsubscribe(
            Subscription(
                getUserId(),
                subscribedId
            )
        )
    }

    fun getUserById(userId: String): LiveData<User> = userRepository.getUserById(userId)

    private fun getUserId(): String =
        authRepository.getCurrentUser()?.id ?: throw IllegalStateException("Not authorized")

    suspend fun refreshUsers() = userRepository.refreshUsers(getUserId())

    fun getAllUsers(): LiveData<List<User>> = userRepository.getUsers(getUserId())

    suspend fun addUser(user: User) = userRepository.addUser(user)

    suspend fun updateUser(user: User) = userRepository.updateUser(user)

    fun isUserAuthenticated(): LiveData<Boolean> = authRepository.isUserAuthenticated()
}