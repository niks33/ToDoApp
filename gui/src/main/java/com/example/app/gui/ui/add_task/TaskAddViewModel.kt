package com.example.app.gui.ui.add_task

import com.example.app.base.gui.viewmodel.BaseViewModel
import com.example.app.database.Task
import com.example.app.database.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * A ViewModel class to interact with Database via injection of TaskRepository
 * Extending BaseViewModel to allow easy access of coroutine method
 * This View Model allow to Add Task into tasks table.
 * */

@HiltViewModel
class TaskAddViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): BaseViewModel(){

    internal fun addTask(task: Task){
        launchDBOperation {
            taskRepository.insertTask(task)
        }
    }

}