package com.example.runningapp.di

import android.content.Context
import androidx.room.Room
import com.example.runningapp.data.network.api.FirebaseApi
import com.example.runningapp.data.network.api.FirebaseApiImpl
import com.example.runningapp.data.repositories.FirebaseAuthRepository
import com.example.runningapp.data.repositories.FirebaseSprintRepository
import com.example.runningapp.data.repositories.FirebaseUserRepository
import com.example.runningapp.data.repositories.FusedLocationRepositoryImpl
import com.example.runningapp.data.room.RunDatabase
import com.example.runningapp.data.room.daos.UsersDAO
import com.example.runningapp.domain.repositories.AuthRepository
import com.example.runningapp.domain.repositories.FusedLocationRepository
import com.example.runningapp.domain.repositories.SprintRepository
import com.example.runningapp.domain.repositories.UserRepository
import com.example.runningapp.domain.usecases.RunUseCase
import com.example.runningapp.domain.usecases.UserUseCase
import com.example.runningapp.presentation.running.RunNotification
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@Singleton
class AppModule {

    @Singleton
    @Provides
    fun provideUsersDao(db: RunDatabase): UsersDAO = db.usersDAO

    @Singleton
    @Provides
    fun provideDatabase(context: Context): RunDatabase = Room.databaseBuilder(
        context,
        RunDatabase::class.java,
        "runDB"
    ).build()

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideAuthenticationRepository(firebaseAuth: FirebaseAuth): AuthRepository =
        FirebaseAuthRepository(firebaseAuth)

    @Singleton
    @Provides
    fun provideNotification(context: Context): RunNotification =
        RunNotification(context)

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Singleton
    @Provides
    fun provideFirebaseApi(firebaseFirestore: FirebaseFirestore): FirebaseApi =
        FirebaseApiImpl(firebaseFirestore)

    @Singleton
    @Provides
    fun provideUserRepository(
        firebaseApi: FirebaseApi,
        usersDao: UsersDAO
    ): UserRepository =
        FirebaseUserRepository(firebaseApi, usersDao)

    @Singleton
    @Provides
    fun provideUserUseCase(
        authRepository: AuthRepository,
        userRepository: UserRepository
    ): UserUseCase = UserUseCase(
        authRepository = authRepository,
        userRepository = userRepository
    )

    @Singleton
    @Provides
    fun provideFusedLocationClient(context: Context): FusedLocationProviderClient =
        FusedLocationProviderClient(context)

    @Singleton
    @Provides
    fun provideLocationRepository(
        fusedLocationProviderClient: FusedLocationProviderClient
    ): FusedLocationRepository =
        FusedLocationRepositoryImpl(fusedLocationProviderClient)

    @Singleton()
    @Provides()
    @Named("IO")
    fun provideCoroutineContext(): CoroutineContext = SupervisorJob() + Dispatchers.IO

    @Singleton
    @Provides
    fun provideSprint(firebaseFirestore: FirebaseFirestore): SprintRepository =
        FirebaseSprintRepository(firebaseFirestore)

    @Singleton
    @Provides
    fun provideRunUseCase(
        sprintRepository: SprintRepository,
        authRepository: AuthRepository
    ): RunUseCase = RunUseCase(
        sprintRepository,
        authRepository
    )
}