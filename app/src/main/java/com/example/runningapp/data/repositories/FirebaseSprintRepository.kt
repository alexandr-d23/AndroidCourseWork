package com.example.runningapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.runningapp.data.mappers.sprintLocalToSprint
import com.example.runningapp.data.mappers.sprintRemoteToSprint
import com.example.runningapp.data.mappers.sprintRemoteToSprintLocal
import com.example.runningapp.data.mappers.sprintToSprintRemote
import com.example.runningapp.data.network.api.FirebaseApi
import com.example.runningapp.data.network.model.SprintRemote
import com.example.runningapp.data.room.daos.SprintsDAO
import com.example.runningapp.domain.model.Sprint
import com.example.runningapp.domain.repositories.SprintRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseSprintRepository(
    private val firebaseApi: FirebaseApi,
    private val sprintsDAO: SprintsDAO
) : SprintRepository {

    override suspend fun saveSprint(sprint: Sprint) = firebaseApi.saveSprint(sprint)

    override suspend fun getSprints(userId: String): LiveData<List<Sprint>> {
        sprintsDAO.addSprints(firebaseApi.getSprints(userId).map (::sprintRemoteToSprintLocal))
        return sprintsDAO.getSprintsByUserId(userId).map {
            it.map (::sprintLocalToSprint)
        }
    }

}