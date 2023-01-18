package com.dart69.todolist.createlist.di

import com.dart69.todolist.createlist.presentation.CreateListScreenStateFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface CreateListModule {

    @Binds
    fun bindScreenStateFactory(
        factory: CreateListScreenStateFactory.Default
    ): CreateListScreenStateFactory
}