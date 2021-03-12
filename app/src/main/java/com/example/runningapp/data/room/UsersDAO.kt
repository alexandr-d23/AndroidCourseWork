package com.example.runningapp.data.room

import androidx.room.*
import com.example.runningapp.data.room.entities.Sprint
import com.example.runningapp.data.room.entities.User
import com.example.runningapp.data.room.entities.relations.UserWithSprints
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface UsersDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSprint(sprint: Sprint) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSprints(list: List<Sprint>) : Completable

    @Query("SELECT * FROM user")
    fun getUsers(): Flowable<List<User>>

    @Query("SELECT * FROM Sprint")
    fun getSprints(): Flowable<List<Sprint>>

    @Query("SELECT * FROM user")
    fun getUsersWithSprints():Flowable<List<UserWithSprints>>
}