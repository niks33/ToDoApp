package com.example.app.gui.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.example.app.base.gui.activity.BaseActivity
import com.example.app.gui.R
import com.example.app.gui.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * This is a launcher activity class.
 * This activity class is using layout file ${R.layout.activity_main}.
 * Navigation graph, ${main_nav_graph} is injected into same layout file for fragment management.
 * */

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    // Implement abstract variable
    override val layoutResId: Int = R.layout.activity_main
    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivityMainBinding::inflate
    override val binding get() : ActivityMainBinding = super.binding as ActivityMainBinding

    /**
     *  This variable is used to sort the task list.
     *  By default list is sorted by 'Created' as per the list ${sortTypes}
     */
    internal var sortType = 1

    //override onCreate to set the theme
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
    }

}