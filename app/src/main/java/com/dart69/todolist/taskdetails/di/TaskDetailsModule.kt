package com.dart69.todolist.taskdetails.di

import com.dart69.todolist.taskdetails.presentation.TaskDetailsScreenState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface TaskDetailsModule {

    @Binds
    fun bindCreator(
        creator: TaskDetailsScreenState.Creator.Default
    ): TaskDetailsScreenState.Creator
}