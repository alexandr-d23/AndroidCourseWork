package com.example.runningapp.data.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.runningapp.data.room.model.SprintLocal

@Dao
interface SprintsDAO {

    @Query("SELECT * FROM SprintLocal where userId = :userId")
    fun getSprintsByUserId(userId: String): LiveData<List<SprintLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSprint(sprint: SprintLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSprints(list: List<SprintLocal>)
}