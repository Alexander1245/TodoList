package com.dart69.todolist.splash.domain.usecase

import com.dart69.todolist.greetings.domain.AppSettingsRepository
import javax.inject.Inject

interface RunAppFirstTimeUseCase {
    suspend operator fun invoke()

    class Default @Inject constructor(
        private val repository: AppSettingsRepository
    ) : RunAppFirstTimeUseCase {

        override suspend fun invoke() = repository.runAppFirstTime()
    }
}