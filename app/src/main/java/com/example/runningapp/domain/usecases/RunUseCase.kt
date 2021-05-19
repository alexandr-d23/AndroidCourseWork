package com.example.runningapp.domain.usecases

import android.util.Log
import com.example.runningapp.domain.model.Sprint
import com.example.runningapp.domain.repositories.AuthRepository
import com.example.runningapp.domain.repositories.SprintRepository
import com.example.runningapp.presentation.model.SprintItem

class RunUseCase(
    private val sprintRepository: SprintRepository,
    private val authRepository: AuthRepository
) {
    suspend fun saveSprint(sprintItem: SprintItem) {
        sprintItem.userId = authRepository.getCurrentUser()?.id
            ?: throw IllegalStateException("Not authorized").apply {
                printStackTrace()
            }
        val sprint = Sprint(
            userId = sprintItem.userId,
            secondsRun = sprintItem.secondsRun.toSeconds(),
            dateTime = sprintItem.dateTime,
            avgSpeed = sprintItem.avgSpeed,
            distance = sprintItem.distance
        )
        sprintRepository.saveSprint(sprint)
    }

    suspend fun getAuthSprints(): List<Sprint> =
        sprintRepository.getSprints(
            authRepository.getCurrentUser()?.id
                ?: throw IllegalStateException("Not authorized")
        ).also {
            Log.d("MYTAG", "RunUseCase getAuthSprints() list size: ${it.size}")
        }

}