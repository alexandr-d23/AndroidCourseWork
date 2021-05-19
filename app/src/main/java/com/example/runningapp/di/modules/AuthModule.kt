package com.example.runningapp.di.modules

import com.example.runningapp.data.repositories.FirebaseAuthRepository
import com.example.runningapp.domain.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthModule {
    @Singleton
    @Provides
    fun provideAuthenticationRepository(firebaseAuth: FirebaseAuth): AuthRepository =
        FirebaseAuthRepository(firebaseAuth)

}