package com.example.app.gui

import javax.inject.Qualifier

/*
* Providing different App's matrix implemented in app's module
* */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppVersionCode

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppVersionName

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppBuildName

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppReleaseTimestamp