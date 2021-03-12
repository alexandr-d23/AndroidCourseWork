package com.example.runningapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.runningapp.data.room.entities.*

@Database(
    entities = [
        User::class,
        Sprint::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTimeConverter::class)
abstract class RunDatabase : RoomDatabase() {
    abstract val usersDAO: UsersDAO

    companion object {
        @Volatile
        private var instance: RunDatabase? = null

        fun getInstance(context: Context): RunDatabase {
            synchronized(this) {
                return instance ?: Room.databaseBuilder(
                    context,
                    RunDatabase::class.java,
                    "runDB"
                ).build().also {
                    instance = it
                }
            }

        }
    }
}