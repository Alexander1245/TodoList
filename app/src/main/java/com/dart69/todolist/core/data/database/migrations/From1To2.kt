package com.dart69.todolist.core.data.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class From1To2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE new_TaskListEntity(" +
                "id INTEGER NOT NULL," +
                "name TEXT NOT NULL," +
                "PRIMARY KEY(id))")

        database.execSQL("INSERT INTO new_TaskListEntity(name)" +
                "SELECT name FROM TaskListEntity")

        database.execSQL("DROP TABLE TaskListEntity")
        database.execSQL("ALTER TABLE new_TaskListEntity RENAME TO TaskListEntity")
    }
}