package com.example.runningapp.domain.repositories

import com.example.runningapp.domain.model.Sprint

interface SprintRepository {
    suspend fun saveSprint(sprint: Sprint)
    suspend fun getSprints(userId: String): List<Sprint>
}