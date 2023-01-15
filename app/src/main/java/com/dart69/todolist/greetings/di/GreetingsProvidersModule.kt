package com.dart69.todolist.greetings.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavDirections
import com.dart69.todolist.greetings.presentation.GreetingsFragmentDirections
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

private const val SETTINGS_STORE = "settings_data_store"

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
    SETTINGS_STORE)

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HomeDirectionFromGreetings

@Module
@InstallIn(SingletonComponent::class)
object GreetingsProvidersSingletonModule {

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.settingsDataStore
}

@Module
@InstallIn(ViewModelComponent::class)
object GreetingsProvidersModule {

    @Provides
    @HomeDirectionFromGreetings
    fun provideHomeDirection(): NavDirections =
        GreetingsFragmentDirections.actionGreetingsFragmentToHomeFragment()
}