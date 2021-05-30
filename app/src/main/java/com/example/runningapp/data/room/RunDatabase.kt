package com.example.runningapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.runningapp.data.room.daos.SprintsDAO
import com.example.runningapp.data.room.daos.UsersDAO
import com.example.runningapp.data.room.model.*

@Database(
    entities = [
        UserLocal::class,
        SprintLocal::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RunDatabase : RoomDatabase() {
    abstract val usersDAO: UsersDAO

    abstract val sprintsDAO: SprintsDAO
}