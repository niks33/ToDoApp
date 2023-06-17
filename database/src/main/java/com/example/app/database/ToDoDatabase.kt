package com.example.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * A Room Database class.
 * */

@Database(entities = [Task::class], version = 1)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}