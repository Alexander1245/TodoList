package com.dart69.todolist.core.di

import android.content.Context
import androidx.room.Room
import com.dart69.todolist.core.data.database.AppDataBase
import com.dart69.todolist.core.data.database.migrations.From1To2
import com.dart69.todolist.core.data.database.migrations.From4To5
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

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InitialQuery

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
        .addMigrations(
            From1To2(),
            From4To5(),
        )
        .build()

    @Provides
    @Singleton
    fun provideLogger(): Logger = Logger.Console

    @Provides
    @InitialQuery
    fun provideStringQuery(): String = ""
}