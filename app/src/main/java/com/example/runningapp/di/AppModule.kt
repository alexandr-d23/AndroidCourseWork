package com.example.runningapp.di

import android.content.Context
import com.example.runningapp.data.room.RunDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideUsersDao(db: RunDatabase) = db.usersDAO

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = RunDatabase.getInstance(context)
}