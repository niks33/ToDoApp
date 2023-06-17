package com.example.app.base.gui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.base.displayName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * An abstract class extending ViewModel. Use this class to create ViewModels.
 * This class provide launchDBOperation() method which is a coroutines to execute database operations
 * Logs are added to capture the lifecycle of view model.
 * */

abstract class BaseViewModel : ViewModel() {

    init {
        Timber.d("init: $displayName")
    }

    protected val _failure: MutableLiveData<Exception> by lazy { MutableLiveData() }

    val failure: LiveData<Exception> get() = _failure

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared: $displayName")
    }

    protected fun launchDBOperation(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Exception) {
                _failure.postValue(e)
            }
        }
    }

}