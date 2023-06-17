package com.example.app.database

import androidx.lifecycle.LiveData
import androidx.room.Insert
import javax.inject.Inject

/**
 * This class to access TaskDao's interface methods. Injecting TaskDao as constructor dependency injection.
 * This class can be inject into another class to perform database operations through TaskDao.
 * */

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
) {

    fun getAllTask(): LiveData<List<Task>> {
        return taskDao.getAllTask()
    }

    fun insertTask(task: Task) = taskDao.insertTask(task)

    fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

    fun updateTask(taskId: Int, isComplete: Boolean) {
        taskDao.updateTaskCompletion(taskId, isComplete)
    }
}