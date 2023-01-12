package com.dart69.todolist.splash.di

import androidx.navigation.NavDirections
import com.dart69.todolist.splash.presentation.SplashFragmentDirections
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TimeOut

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GreetingsDirection

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HomeDirection

@Module
@InstallIn(ViewModelComponent::class)
object SplashProvidersModule {

    @Provides
    @TimeOut
    fun provideTimeOut(): Long = 1500L

    @GreetingsDirection
    @Provides
    fun provideGreetingsDirection(): NavDirections =
        SplashFragmentDirections.actionSplashFragmentToGreetingsFragment()

    @HomeDirection
    @Provides
    fun provideHomeDirection(): NavDirections =
        SplashFragmentDirections.actionSplashFragmentToHomeFragment()
}