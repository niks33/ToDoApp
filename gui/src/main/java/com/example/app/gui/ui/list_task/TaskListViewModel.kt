package com.example.app.gui.ui.list_task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.app.base.gui.viewmodel.BaseViewModel
import com.example.app.database.Task
import com.example.app.database.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * A ViewModel class to interact with Database via injection of TaskRepository
 * Extending BaseViewModel to allow easy access of coroutine method
 * This View Model allow to Get,Delete and Update Task into tasks table.
 * */

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): BaseViewModel(){

    private val _task: MutableLiveData<List<Task>> by lazy { MutableLiveData<List<Task>>() }
    internal var task: LiveData<List<Task>> = _task

    internal fun getAllTask(){
        launchDBOperation {
           task = taskRepository.getAllTask()
        }
    }

    internal fun deleteTask(taskId: Int){
        launchDBOperation {
            taskRepository.deleteTask(taskId)
        }
    }

    internal fun updateTask(taskId: Int, isComplete: Boolean){
        launchDBOperation {
            taskRepository.updateTask(taskId, isComplete)
        }
    }

}