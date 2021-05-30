package com.example.runningapp.di.modules

import com.example.runningapp.data.network.api.FirebaseApi
import com.example.runningapp.data.repositories.FirebaseSprintRepository
import com.example.runningapp.data.repositories.FirebaseUserRepository
import com.example.runningapp.data.repositories.FusedLocationRepositoryImpl
import com.example.runningapp.data.room.daos.SprintsDAO
import com.example.runningapp.data.room.daos.UsersDAO
import com.example.runningapp.domain.repositories.AuthRepository
import com.example.runningapp.domain.repositories.FusedLocationRepository
import com.example.runningapp.domain.repositories.SprintRepository
import com.example.runningapp.domain.repositories.UserRepository
import com.example.runningapp.domain.usecases.UserUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        firebaseApi: FirebaseApi,
        usersDao: UsersDAO
    ): UserRepository =
        FirebaseUserRepository(firebaseApi, usersDao)

    @Singleton
    @Provides
    fun provideLocationRepository(
        fusedLocationProviderClient: FusedLocationProviderClient
    ): FusedLocationRepository =
        FusedLocationRepositoryImpl(fusedLocationProviderClient)

    @Singleton
    @Provides
    fun provideSprintRepository(firebaseApi: FirebaseApi, sprintsDAO: SprintsDAO): SprintRepository =
        FirebaseSprintRepository(firebaseApi, sprintsDAO)
}