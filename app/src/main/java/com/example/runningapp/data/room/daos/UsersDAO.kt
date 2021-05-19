package com.example.runningapp.data.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.runningapp.data.room.model.SprintLocal
import com.example.runningapp.data.room.model.UserLocal
import com.example.runningapp.data.room.relations.UserWithSprints
import com.example.runningapp.domain.model.Sprint

@Dao
interface UsersDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserLocal)

    @Query("SELECT * FROM userlocal")
    fun getUsers(): LiveData<List<UserLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(list: List<UserLocal>)

    @Query("SELECT * FROM userlocal")
    fun getUsersWithSprints(): LiveData<List<UserWithSprints>>

    @Query("SELECT * FROM userlocal WHERE id = :userId")
    fun getUserWithSprintsById(userId: String): LiveData<UserWithSprints>
}