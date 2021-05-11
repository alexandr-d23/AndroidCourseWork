package com.example.runningapp.domain.usecases

import android.util.Log
import com.example.runningapp.data.network.model.Subscription
import com.example.runningapp.domain.repositories.AuthRepository
import com.example.runningapp.domain.repositories.UserRepository
import com.example.runningapp.domain.model.User
import java.lang.IllegalStateException

class UserUseCaseImpl(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : UserUseCase {

    override suspend fun updateUsername(name: String) {
        authRepository.updateUsername(name)
        authRepository.getCurrentUser()?.let {
            userRepository.updateUser(it.also {
                it.name = name
            })
        }
    }

    override suspend fun signUp(user: User): User? {
        val userRes = authRepository.signUp(user)
        Log.d("MYTAG", "UserUseCaseImpl sighUp(): ${userRes}")
        userRes?.let {
            userRepository.addUser(it)
        }
        return userRes
    }

    override suspend fun signIn(user: User): User? = authRepository.signIn(user)

    override suspend fun getCurrentUser(): User? {
        return authRepository.getCurrentUser()
    }

    override suspend fun signOut() = authRepository.signOut()

    override suspend fun subscribe(subscribedId: String) {
        Log.d("MYTAG", "UserUseCaseImpl subscribe() : ${subscribedId}")
        userRepository.subscribe(
            Subscription(
                getCurrentUser()?.id ?: throw IllegalStateException("Not authorized"),
                subscribedId
            )
        )
    }

    override suspend fun unsubscribe(subscribedId: String) {
        Log.d("MYTAG", "UserUseCaseImpl unsubscribe() : ${subscribedId}")
        userRepository.unsubscribe(
            Subscription(
                getCurrentUser()?.id ?: throw IllegalStateException("Not authorized"),
                subscribedId
            )
        )
    }

    override suspend fun getUsers(): List<User> = userRepository.getUsers()

    override suspend fun addUser(user: User) = userRepository.addUser(user)

    override suspend fun updateUser(user: User) = userRepository.updateUser(user)
}