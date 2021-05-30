package com.example.runningapp.di.modules

import android.content.Context
import androidx.room.Room
import com.example.runningapp.data.room.RunDatabase
import com.example.runningapp.data.room.daos.SprintsDAO
import com.example.runningapp.data.room.daos.UsersDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): RunDatabase = Room.databaseBuilder(
        context,
        RunDatabase::class.java,
        "runDB"
    ).build()

    @Singleton
    @Provides
    fun provideUsersDao(db: RunDatabase): UsersDAO = db.usersDAO

    @Singleton
    @Provides
    fun provideSprintsDao(db: RunDatabase): SprintsDAO = db.sprintsDAO
}