package com.example.runningapp.presentation.di

import androidx.lifecycle.ViewModel
import com.example.runningapp.presentation.profile.ProfileViewModel
import com.example.runningapp.presentation.signin.SignInViewModel
import com.example.runningapp.presentation.signup.SignUpViewModel
import com.example.runningapp.presentation.userdetails.UserDetailsViewModel
import com.example.runningapp.presentation.users.UsersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ScreenModule {
    @Binds
    @IntoMap
    @VMKey(ProfileViewModel::class)
    fun provideProfilePetVM(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @VMKey(SignInViewModel::class)
    fun provideSignInVM(viewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @VMKey(SignUpViewModel::class)
    fun provideSignUpVM(viewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @VMKey(UserDetailsViewModel::class)
    fun provideUserDetailsVM(viewModel: UserDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @VMKey(UsersViewModel::class)
    fun provideUsersVM(viewModel: UsersViewModel): ViewModel


}