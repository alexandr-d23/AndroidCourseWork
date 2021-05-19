package com.example.runningapp.domain.repositories

import androidx.lifecycle.LiveData
import com.example.runningapp.domain.model.User

interface AuthRepository {
    suspend fun updateUsername(name: String)
    suspend fun signUp(user: User): User?
    suspend fun signIn(user: User): User?
    fun getCurrentUser(): User?
    suspend fun signOut()
    fun isUserAuthenticated(): LiveData<Boolean>
}