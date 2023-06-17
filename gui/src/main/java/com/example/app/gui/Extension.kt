package com.example.app.gui

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

/*
* Extension of different class
* */

// To check whether TextInputLayout has empty text
fun TextInputLayout.isBlank(): Boolean{
    return this.editText?.text?.isBlank() == true
}

// To get TextInputLayout text
fun TextInputLayout.getText(): String{
    return this.editText?.text.toString().trim()
}

// To display Snack bar with custom message and action
fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

// To add action into Snack bar with custom text and listener
fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    setTextColor(Color.WHITE)
    setBackgroundTint(ContextCompat.getColor(context, R.color.orange_dark))
    setActionTextColor(ContextCompat.getColor(context, R.color.white))
}


// To add hide keyboard from context
private fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

// To add hide keyboard from fragment
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

// To get current local time in 00:00 AM format
fun getCurrentTime(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return dateFormat.format(calendar.time)
}

// To check whether passed time (in-format 00:00 AM) is past time
fun isTimePast(currentTime: String, targetTime: String): Boolean {
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val currentCalendar = Calendar.getInstance()
    val targetCalendar = Calendar.getInstance()
    currentCalendar.time = dateFormat.parse(currentTime) as Date
    targetCalendar.time = dateFormat.parse(targetTime) as Date
    return targetCalendar.before(currentCalendar)
}