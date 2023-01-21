package com.dart69.todolist.createlist.di

import com.dart69.todolist.core.coroutines.Results
import com.dart69.todolist.core.coroutines.SearcherBuilder
import com.dart69.todolist.core.coroutines.VariadicSearcherBuilder
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

    @Binds
    fun bindSearcherBuilder(
        builder: VariadicSearcherBuilder.Default<Results<Boolean>>
    ): SearcherBuilder<Results<Boolean>>
}