package com.example.app.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * A Room database 'Dao' class which have the database operation.
 * */

@Dao
interface TaskDao {

    // Operation to get all the data from tasks table.
    // Using Room DB feature to emit Live Data object
    @Query("SELECT * FROM tasks")
    fun getAllTask(): LiveData<List<Task>>

    //Operation to insert data into tasks table
    @Insert
    fun insertTask(task: Task)

    //Operation to delete data based on taskId from tasks table
    @Query("DELETE FROM tasks WHERE id = :taskId")
    fun deleteTask(taskId: Int)

    //Operation to update data based on taskId from tasks table
    @Query("UPDATE tasks SET taskComplete = :taskComplete WHERE id = :id")
    fun updateTaskCompletion(id: Int, taskComplete: Boolean): Int
}