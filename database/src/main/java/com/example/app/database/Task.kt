package com.example.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A Room database 'Entity' data class which will also work as the data model class.
 * Implemented Comparator object to allow sorting of Tasks inside a collection
 * */

@Entity(tableName = "tasks")
data class Task(
    val title: String,
    val time: String,
    val timeSuffix: String,
    val taskComplete: Boolean = false,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    companion object {
        var sortByTitle = Comparator<Task> { t1, t2 -> t1.title.compareTo(t2.title) }
        var sortByStatus = Comparator<Task> { t1, t2 -> t1.taskComplete.compareTo(t2.taskComplete) }
        var sortById = Comparator<Task> { t1, t2 -> t1.id?.let { t2.id?.compareTo(it) } ?: 0 }
    }
}