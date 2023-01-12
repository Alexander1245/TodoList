package com.dart69.todolist.splash.di

import com.dart69.todolist.splash.domain.usecase.IsAppFirstRunUseCase
import com.dart69.todolist.splash.domain.usecase.RunAppFirstTimeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface SplashModule {

    @Binds
    fun bindIsAppFirstRunUseCase(
        useCase: IsAppFirstRunUseCase.Default
    ): IsAppFirstRunUseCase

    @Binds
    fun bindRunAppFirstTimeUseCase(
        useCase: RunAppFirstTimeUseCase.Default
    ): RunAppFirstTimeUseCase
}