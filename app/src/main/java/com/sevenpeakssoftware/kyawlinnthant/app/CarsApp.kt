package com.sevenpeakssoftware.kyawlinnthant.app

import android.app.Application
import com.sevenpeakssoftware.kyawlinnthant.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CarsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}