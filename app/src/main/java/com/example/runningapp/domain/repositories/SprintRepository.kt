package com.example.runningapp.domain.usecases.repositories

import androidx.lifecycle.LiveData
import com.example.runningapp.data.room.entities.Sprint

interface SprintRepository {
    suspend fun saveSprint(userId: String, sprint: Sprint)
    suspend fun getSprints(userId: String): LiveData<List<Sprint>>
}