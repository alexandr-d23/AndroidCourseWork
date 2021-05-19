package com.example.runningapp.di.modules

import com.example.runningapp.domain.repositories.AuthRepository
import com.example.runningapp.domain.repositories.SprintRepository
import com.example.runningapp.domain.repositories.UserRepository
import com.example.runningapp.domain.usecases.RunUseCase
import com.example.runningapp.domain.usecases.UserUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

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
    fun provideRunUseCase(
        sprintRepository: SprintRepository,
        authRepository: AuthRepository
    ): RunUseCase = RunUseCase(
        sprintRepository,
        authRepository
    )
}