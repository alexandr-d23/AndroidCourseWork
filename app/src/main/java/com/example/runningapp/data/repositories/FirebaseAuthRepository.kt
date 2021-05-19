package com.example.runningapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.runningapp.data.mappers.userToUserRemote
import com.example.runningapp.domain.model.User
import com.example.runningapp.domain.repositories.AuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val auth: FirebaseAuth
) : AuthRepository {

    private val isAuthenticated: MutableLiveData<Boolean> = MutableLiveData(auth.currentUser != null)

    init {
        observeAuthStateListener()
    }

    override suspend fun updateUsername(name: String) {
        updateUserName(name)
    }

    override suspend fun signUp(user: User): User? {
        Log.d("MYTAG", "FirebaseAuth    Repository signUp(): ${user}")
        auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener {

        }.await()
        updateUserName(user.name)?.await()
        return auth.currentUser?.let {
            getUserFromFirebaseUser(it)
        }
    }


    private fun observeAuthStateListener(){
        auth.addAuthStateListener {
            isAuthenticated.postValue(auth.currentUser != null)
        }
    }

    private fun updateUserName(name: String?): Task<Void>? {
        val updates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        return auth.currentUser?.updateProfile(updates)
    }

    override suspend fun signIn(user: User): User? {
        auth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener {

        }.await()
        return auth.currentUser?.let {
            getUserFromFirebaseUser(it)
        }
    }

    override fun getCurrentUser(): User? = auth.currentUser?.let {
        getUserFromFirebaseUser(it)
    }

    private fun getUserFromFirebaseUser(firebaseUser: FirebaseUser): User =
        with(firebaseUser) {
            User(
                id = uid,
                email = email,
                name = displayName
            )
        }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun isUserAuthenticated(): LiveData<Boolean> = isAuthenticated
}