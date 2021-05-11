package com.example.runningapp.domain.usecases.repositories

import com.example.runningapp.data.room.entities.User

interface AuthRepository {
    suspend fun updateUsername(name: String)
    suspend fun signUp(user: User): User?
    suspend fun signIn(user: User): User?
    suspend fun getCurrentUser(): User?
    suspend fun signOut()
}