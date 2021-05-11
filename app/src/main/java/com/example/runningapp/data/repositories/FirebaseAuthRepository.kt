package com.example.runningapp.data.repositories

import android.util.Log
import com.example.runningapp.data.room.entities.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class FirebaseUserRepository(
    private val auth: FirebaseAuth
) : UserRepository {
    override suspend fun updateUsername(name: String) {
        updateUserName(name)
    }

    override suspend fun signUp(user: User): User? {
        Log.d("MYTAG", "FirebaseUserRepository signUp(): ${user}")
        val task = auth.createUserWithEmailAndPassword(user.email, user.password)
        task.addOnCompleteListener {
        }.await()
        updateUserName(user.name)?.await()
        return auth.currentUser?.let {
            getUserFromFirebaseUser(it)
        }
    }

    private fun updateUserName(name: String): Task<Void>? {
        val updates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        return auth.currentUser?.updateProfile(updates)
    }

    override suspend fun signIn(user: User): User? {
        val task = auth.signInWithEmailAndPassword(user.email, user.password)
        task.addOnCompleteListener {

        }.await()
        return auth.currentUser?.let {
            getUserFromFirebaseUser(it)
        }
    }

    override suspend fun getCurrentUser(): User? = auth.currentUser?.let {
        getUserFromFirebaseUser(it)
    }

    private fun getUserFromFirebaseUser(firebaseUser: FirebaseUser): User {
        val user = firebaseUser.let {
            User(
                email = it.email ?: "",
                name = it.displayName ?: "",
                id = it.uid
            )
        }
        return user
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}