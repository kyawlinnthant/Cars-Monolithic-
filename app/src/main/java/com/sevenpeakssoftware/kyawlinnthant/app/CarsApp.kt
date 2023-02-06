package com.sevenpeakssoftware.kyawlinnthant.app

import androidx.multidex.MultiDexApplication
import com.sevenpeakssoftware.kyawlinnthant.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CarsApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}