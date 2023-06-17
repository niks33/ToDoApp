package com.example.app.base.gui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.example.app.base.displayName
import timber.log.Timber

/**
 * An abstract class extending FragmentActivity. This class can be use to create activities with minimal code redundancies.
 * View Binding is already applied with resource file and same resource file is inflated.
 * */

abstract class BaseActivity : FragmentActivity() {

    @get:LayoutRes
    protected abstract val layoutResId: Int

    protected abstract val bindingInflater: (LayoutInflater) -> ViewBinding

    private var _binding: ViewBinding? = null

    protected open val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.v("onCreate IN $displayName")
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
        Timber.v("onCreate OUT $displayName")
    }

    override fun onDestroy() {
        Timber.d("onDestroy: IN $displayName")
        super.onDestroy()
        Timber.d("onDestroy: OUT $displayName")
    }

}