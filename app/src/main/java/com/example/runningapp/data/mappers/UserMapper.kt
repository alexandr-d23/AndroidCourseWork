package com.example.runningapp.data.mappers

import android.util.Log
import com.example.runningapp.data.network.model.UserRemote
import com.example.runningapp.data.room.model.UserLocal
import com.example.runningapp.data.room.relations.UserWithSprints
import com.example.runningapp.domain.model.User
import java.lang.IllegalStateException

fun userToUserRemote(user: User) : UserRemote{
    Log.d("MYTAG", "userToRemoteUser() ${user}")
    if(user.id == null) throw IllegalStateException("Id must be not null")
    if(user.email == null) throw IllegalStateException("Email must be not null")
    if(user.name == null) throw IllegalStateException("Name must be not null")
    return UserRemote(user.id!!, user.email!!, user.name!!)
}

fun userRemoteToUserlocal(userRemote: UserRemote, isSubscribed: Boolean): UserLocal =
    UserLocal(
        id = userRemote.id,
        name = userRemote.name,
        email = userRemote.email,
        isSubscribed = isSubscribed
    )

fun userWithSprintsToUser(userWithSprints: UserWithSprints): User =
    User(
        id = userWithSprints.user.id,
        email = userWithSprints.user.email,
        name = userWithSprints.user.name,
        sprints = userWithSprints.list.map(::sprintLocalToSprint),
        isSubscribed = userWithSprints.user.isSubscribed
    )
