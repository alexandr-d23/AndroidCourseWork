package com.example.runningapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.runningapp.data.room.entities.Sprint
import com.example.runningapp.data.room.entities.User
import com.example.runningapp.data.room.entities.relations.UserWithSprints

@Dao
interface UsersDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSprint(sprint: Sprint)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSprints(list: List<Sprint>)

    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM Sprint where userId = :userId")
    suspend fun getSprintsByUserId(userId: String): List<Sprint>

    @Query("SELECT * FROM user where id = :userId")
    suspend fun getUsersWithSprints(userId: String): List<UserWithSprints>
}