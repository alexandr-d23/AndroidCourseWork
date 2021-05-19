package com.example.runningapp.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.runningapp.domain.usecases.RunUseCase
import com.example.runningapp.domain.usecases.UserUseCase
import com.example.runningapp.presentation.authorization.AuthorizationViewModel
import com.example.runningapp.presentation.profile.ProfileViewModel
import com.example.runningapp.presentation.signin.SignInViewModel
import com.example.runningapp.presentation.signup.SignUpViewModel
import com.example.runningapp.presentation.userdetails.UserDetailsViewModel
import com.example.runningapp.presentation.users.UsersViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class ViewModelFactory @Inject constructor(
    private val userUseCase: UserUseCase,
    private val runUseCase: RunUseCase,
    @Named("IO")
    private val coroutineContext: CoroutineContext
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(userUseCase, coroutineContext) as? T
                    ?: throw IllegalArgumentException("Unknown viewmodel class")
            }
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> {
                SignInViewModel(userUseCase, coroutineContext) as? T
                    ?: throw IllegalArgumentException("Unknown viewmodel class")
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userUseCase, runUseCase, coroutineContext) as? T
                    ?: throw IllegalArgumentException("Unknown viewmodel class")
            }
            modelClass.isAssignableFrom(UsersViewModel::class.java) -> {
                UsersViewModel(userUseCase, coroutineContext) as? T
                    ?: throw IllegalArgumentException("Unknown viewmodel class")
            }
            modelClass.isAssignableFrom(AuthorizationViewModel::class.java) -> {
                AuthorizationViewModel(userUseCase) as? T
                    ?: throw IllegalArgumentException("Unknown viewmodel class")
            }
            modelClass.isAssignableFrom(UserDetailsViewModel::class.java) -> {
                UserDetailsViewModel(userUseCase) as? T
                    ?: throw IllegalArgumentException("Unknown viewmodel class")
            }
            else -> {
                throw IllegalArgumentException("Unknown viewmodel class")
            }
        }

}