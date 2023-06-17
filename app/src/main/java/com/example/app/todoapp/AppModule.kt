package com.example.app.todoapp

import com.example.app.gui.AppBuildName
import com.example.app.gui.AppReleaseTimestamp
import com.example.app.gui.AppVersionCode
import com.example.app.gui.AppVersionName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
* This object works as provide for annotated methods. These annotation can be used in any other module.
* */

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Provides
    @AppVersionCode
    internal fun provideAppVersionCode(): Int = BuildConfig.VERSION_CODE

    @Provides
    @AppVersionName
    internal fun provideAppVersionName(): String = BuildConfig.VERSION_NAME

    @Provides
    @AppBuildName
    internal fun provideAppBuildName(): String = "BuildName: " + BuildConfig.BUILD_TYPE

    @Provides
    @AppReleaseTimestamp
    internal fun provideAppReleaseTimestamp(): String = BuildConfig.RELEASE_TIMESTAMP
}