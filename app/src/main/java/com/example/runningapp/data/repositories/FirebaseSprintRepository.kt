package com.example.runningapp.data.repositories

import android.util.Log
import com.example.runningapp.data.mappers.sprintRemoteToSprint
import com.example.runningapp.data.mappers.sprintToSprintRemote
import com.example.runningapp.data.network.model.SprintRemote
import com.example.runningapp.domain.model.Sprint
import com.example.runningapp.domain.repositories.SprintRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseSprintRepository(
    private val firebaseFirestore: FirebaseFirestore
) : SprintRepository {

    private val collectionRef = firebaseFirestore.collection("users")

    override suspend fun saveSprint(sprint: Sprint) {
        val sprintRemote = sprintToSprintRemote(sprint)
        Log.d("MYTAG", "FirebaseSpringRepository saveSprint(): $sprintRemote")
        collectionRef.document(sprintRemote.userId).collection("sprints").add(sprintRemote)
    }

    override suspend fun getSprints(userId: String): List<Sprint> {
        return collectionRef.document(userId).collection("sprints").get()
            .await().documents.map { doc ->
                doc.toObject(SprintRemote::class.java)
                    ?: throw IllegalStateException("Unchecked cast")
            }.map(::sprintRemoteToSprint).also {
                Log.d("MYTAG", "${it.size}")
            }.sortedByDescending {
                it.dateTime
            }
    }

}