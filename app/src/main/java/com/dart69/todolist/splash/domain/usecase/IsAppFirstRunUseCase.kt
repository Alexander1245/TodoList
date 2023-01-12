package com.dart69.todolist.splash.domain.usecase

import com.dart69.todolist.greetings.domain.AppSettingsRepository
import javax.inject.Inject

interface IsAppFirstRunUseCase {
    suspend operator fun invoke(): Boolean

    class Default @Inject constructor(
        private val appSettingsRepository: AppSettingsRepository
    ): IsAppFirstRunUseCase {

        override suspend fun invoke(): Boolean = appSettingsRepository.isAppFirstRun()
    }
}