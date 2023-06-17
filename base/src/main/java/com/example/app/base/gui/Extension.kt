package com.example.app.base.gui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Extension methods of different class
 * */

// Extension method for lifecycle owner(activity/fragments) to observer liveData
fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) =
    liveData.observe(this, Observer(body))

// Extension method for ViewGroup to inflate layouts, mainly used in recycle views
val ViewGroup.layoutInflater: LayoutInflater get() = LayoutInflater.from(context)