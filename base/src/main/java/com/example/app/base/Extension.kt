package com.example.app.base

/**
 * Extension of different class
 * */

// Extension of Any object to show the java class name
val Any.displayName: String
    get() = "${javaClass.simpleName}[${hashCode().toString(16)}]"