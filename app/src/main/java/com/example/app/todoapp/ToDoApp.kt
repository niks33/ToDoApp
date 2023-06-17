package com.example.app.todoapp

import android.app.Application
import com.example.app.base.displayName
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
* Application class with @HiltAndroidApp annotation to tell Dagger components should be generated here
* */
@HiltAndroidApp
internal class ToDoApp: Application() {

    init {
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}