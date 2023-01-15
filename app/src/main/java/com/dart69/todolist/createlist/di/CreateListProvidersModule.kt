package com.dart69.todolist.createlist.di

import android.database.sqlite.SQLiteConstraintException
import com.dart69.todolist.core.data.exceptions.ErrorMapper
import com.dart69.todolist.core.data.exceptions.RoomException
import com.dart69.todolist.core.data.exceptions.toRoomException
import com.dart69.todolist.createlist.data.UniqueIdException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CreateListProvidersModule {

    @Provides
    fun provideErrorMapper(): ErrorMapper<Throwable, RoomException> = ErrorMapper {
        when(it) {
            is SQLiteConstraintException -> UniqueIdException()
            else -> it.toRoomException()
        }
    }
}