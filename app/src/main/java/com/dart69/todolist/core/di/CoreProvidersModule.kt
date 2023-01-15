package com.dart69.todolist.core.di

import android.content.Context
import androidx.room.Room
import com.dart69.todolist.core.data.database.AppDataBase
import com.dart69.todolist.core.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
object CoreProvidersModule {

    @Provides
    @ApplicationScope
    fun provideApplicationScope(): CoroutineScope = MainScope()

    @Provides
    @Singleton
    fun provideAppDataBase(
        @ApplicationContext context: Context,
    ): AppDataBase = Room
        .databaseBuilder(context, AppDataBase::class.java, AppDataBase.NAME)
        .build()

    @Provides
    @Singleton
    fun provideLogger(): Logger = Logger.Console
}