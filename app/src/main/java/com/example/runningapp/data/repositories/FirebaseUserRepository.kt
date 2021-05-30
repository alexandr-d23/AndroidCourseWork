package com.example.runningapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.runningapp.data.mappers.userRemoteToUserlocal
import com.example.runningapp.data.mappers.userWithSprintsToUser
import com.example.runningapp.data.network.api.FirebaseApi
import com.example.runningapp.data.network.model.Subscription
import com.example.runningapp.data.room.daos.UsersDAO
import com.example.runningapp.domain.model.User
import com.example.runningapp.domain.repositories.UserRepository

class FirebaseUserRepository(
    private val firebaseApi: FirebaseApi,
    private val usersDAO: UsersDAO
) : UserRepository {


    override fun getUsers(userId: String): LiveData<List<User>> {
        return usersDAO.getUsersWithSprints().map {
            it.map(::userWithSprintsToUser)
        }
    }

    override fun getUserById(userId: String): LiveData<User?> =
        usersDAO.getUserWithSprintsById(userId).map {
            it?.let {  user ->
                userWithSprintsToUser(user)
            }
        }

    override suspend fun addUser(user: User) {
        firebaseApi.addUser(user)
    }

    override suspend fun updateUser(user: User) {
        firebaseApi.updateUser(user)
    }

    private suspend fun getSubscriptions(subscriberId: String): List<Subscription> =
        firebaseApi.getSubscriptions(subscriberId)

    override suspend fun subscribe(subscription: Subscription) {
        Log.d("MYTAG", "FirebaseUserRepository subscribe() : ${subscription}")
        firebaseApi.subscribe(subscription)
        Log.d("MYTAG", "FirebaseUserRepository subscribe() after api : ${subscription}")
        refreshUsers(subscription.subscriberId)
    }

    override suspend fun unsubscribe(subscription: Subscription) {
        firebaseApi.unsubscribe(subscription)
        refreshUsers(subscription.subscriberId)
    }

    override suspend fun refreshUsers(userId: String) {
        val subscriptions = getSubscriptions(userId)
        var isSubscribed: Boolean
        firebaseApi.getUsers().also { list ->
            usersDAO.addUsers(list.map {
                isSubscribed =
                    subscriptions.find { subscription -> subscription.userId == it.id } != null
                userRemoteToUserlocal(it, isSubscribed)
            })
        }

    }

}