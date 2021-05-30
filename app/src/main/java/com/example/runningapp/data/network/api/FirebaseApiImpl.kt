package com.example.runningapp.data.network.api

import android.util.Log
import com.example.runningapp.data.mappers.sprintRemoteToSprint
import com.example.runningapp.data.mappers.sprintToSprintRemote
import com.example.runningapp.data.mappers.userToUserRemote
import com.example.runningapp.data.network.model.SprintRemote
import com.example.runningapp.data.network.model.Subscription
import com.example.runningapp.data.network.model.UserRemote
import com.example.runningapp.domain.model.Sprint
import com.example.runningapp.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseApiImpl(
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseApi {
    private val usersRef = firebaseFirestore.collection("users")
    private val subscriptionsRef = firebaseFirestore.collection("subscriptions")

    override suspend fun addUser(user: User) {
        val userRemote = userToUserRemote(user)
        usersRef.document(userRemote.id).set(userRemote)
    }

    override suspend fun updateUser(user: User) {
        val userRemote = userToUserRemote(user)
        usersRef.document(userRemote.id).set(user)
    }

    override suspend fun getSubscriptions(subscriberId: String): List<Subscription> {
        return subscriptionsRef.whereEqualTo("subscriberId", subscriberId).get()
            .await().documents.map {
                it.toObject(Subscription::class.java)
                    ?: throw IllegalStateException("Incorrect deserializing")
            }
    }

    override suspend fun subscribe(subscription: Subscription) {
        if (subscriptionsRef.whereEqualTo("subscriberId", subscription.subscriberId)
                .whereEqualTo("userId", subscription.userId).get().await().documents.size == 0
        ) {
            Log.d("MYTAG", "Firebaseapi subscribe() : ${subscription}")
            subscriptionsRef.add(subscription)
        }
    }

    override suspend fun unsubscribe(subscription: Subscription) {
        val documents = subscriptionsRef.whereEqualTo("subscriberId", subscription.subscriberId)
            .whereEqualTo("userId", subscription.userId).get().await().documents
        if (documents.size > 0) {
            Log.d("MYTAG", "FirebaseUserRepository: unsubscribe() : ${subscription}")
            //only 1 document
            val id = documents[0].id
            subscriptionsRef.document(id).delete()
        }
    }

    override suspend fun getUsers(): List<UserRemote> =
        usersRef.get().await().documents.map {
            it.toObject(UserRemote::class.java)
                ?: throw IllegalStateException("Incorrect deserializing")
        }

    override suspend fun saveSprint(sprint: Sprint) {
        val sprintRemote = sprintToSprintRemote(sprint)
        Log.d("MYTAG", "FirebaseSpringRepository saveSprint(): $sprintRemote")
        usersRef.document(sprintRemote.userId).collection("sprints").add(sprintRemote)
    }

    override suspend fun getSprints(userId: String): List<SprintRemote> {
        return usersRef.document(userId).collection("sprints").get()
            .await().documents.map { doc ->
                doc.toObject(SprintRemote::class.java)
                    ?: throw IllegalStateException("Unchecked cast")
            }
    }

}