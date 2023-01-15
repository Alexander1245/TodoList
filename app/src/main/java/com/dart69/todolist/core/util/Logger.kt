package com.dart69.todolist.core.util

import android.util.Log

interface Logger {
    fun log(message: String)

    fun log(exception: Throwable)

    object Console : Logger {
        private const val TAG = "LOGGER"

        override fun log(message: String) {
            Log.d(TAG, message)
        }

        override fun log(exception: Throwable) {
            Log.d(TAG, "${exception.message}\n${exception.stackTraceToString()}")
        }
    }
}