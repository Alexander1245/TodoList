package com.dart69.todolist.core.data.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class From4To5 : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE new_TaskListEntity(" +
                "name TEXT NOT NULL," +
                "PRIMARY KEY(name))")

        database.execSQL("INSERT INTO new_TaskListEntity(name)" +
                "SELECT name FROM TaskListEntity")

        database.execSQL("DROP TABLE TaskListEntity")
        database.execSQL("ALTER TABLE new_TaskListEntity RENAME TO TaskListEntity")
    }
}