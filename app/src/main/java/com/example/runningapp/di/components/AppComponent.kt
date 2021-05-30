package com.example.runningapp.di.components

import android.app.Application
import com.example.runningapp.di.modules.*
import com.example.runningapp.presentation.authorization.AuthActivity
import com.example.runningapp.presentation.common.RunActivity
import com.example.runningapp.presentation.di.ScreenComponent
import com.example.runningapp.presentation.profile.ProfileFragment
import com.example.runningapp.presentation.running.RunFragment
import com.example.runningapp.presentation.running.RunService
import com.example.runningapp.presentation.signin.SignInFragment
import com.example.runningapp.presentation.signup.SignUpFragment
import com.example.runningapp.presentation.userdetails.UserDetailsFragment
import com.example.runningapp.presentation.users.UsersFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        AuthModule::class,
        FirebaseModule::class,
        RoomModule::class,
        RepoModule::class,
        UseCaseModule::class
    ]
)
@Singleton
interface AppComponent {

    fun getScreenComponent(): ScreenComponent.Factory


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}