package com.example.runningapp.presentation.di

import com.example.runningapp.presentation.authorization.AuthActivity
import com.example.runningapp.presentation.authorization.AuthenticationFragment
import com.example.runningapp.presentation.common.RunActivity
import com.example.runningapp.presentation.profile.ProfileFragment
import com.example.runningapp.presentation.running.RunFragment
import com.example.runningapp.presentation.running.RunService
import com.example.runningapp.presentation.signin.SignInFragment
import com.example.runningapp.presentation.signup.SignUpFragment
import com.example.runningapp.presentation.userdetails.UserDetailsFragment
import com.example.runningapp.presentation.users.UsersFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ScreenModule::class])
interface ScreenComponent {

    fun inject(activity: RunActivity)

    fun inject(activity: AuthActivity)

    fun inject(runFragment: RunFragment)

    fun inject(runService: RunService)

    fun inject(signInFragment: SignInFragment)

    fun inject(singUpFragment: SignUpFragment)

    fun inject(usersFragment: UsersFragment)

    fun inject(profileFragment: ProfileFragment)

    fun inject(userDetailsFragment: UserDetailsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ScreenComponent
    }
}