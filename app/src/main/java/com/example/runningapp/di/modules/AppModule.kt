package com.example.runningapp.di.modules

import android.app.Application
import android.content.Context
import com.example.runningapp.presentation.running.RunNotification
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideNotification(context: Context): RunNotification =
        RunNotification(context)

    @Singleton
    @Provides
    fun provideContext(application: Application) = application.applicationContext

    @Singleton
    @Provides
    fun provideFusedLocationClient(context: Context): FusedLocationProviderClient =
        FusedLocationProviderClient(context)

    @Singleton()
    @Provides()
    fun provideCoroutineContext(): CoroutineContext = SupervisorJob() + Dispatchers.IO
}