package com.example.runningapp.domain.repositories

import androidx.lifecycle.LiveData
import com.example.runningapp.domain.model.Sprint

interface SprintRepository {
    suspend fun saveSprint(sprint: Sprint)
    suspend fun getSprints(userId: String): LiveData<List<Sprint>>
}