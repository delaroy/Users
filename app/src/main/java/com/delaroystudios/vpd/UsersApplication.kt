package com.delaroystudios.vpd

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UsersApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
