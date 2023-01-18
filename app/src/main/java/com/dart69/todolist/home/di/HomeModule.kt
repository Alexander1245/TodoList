package com.dart69.todolist.home.di

import com.dart69.todolist.home.data.*
import com.dart69.todolist.home.presentation.HomeScreenStateFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface HomeModule {

    @Binds
    fun bindScreenStateFactory(
        factory: HomeScreenStateFactory.Default
    ): HomeScreenStateFactory
}