package com.andydotdaniel.jajanku

import android.app.Application
import com.andydotdaniel.jajanku.di.initializeDependencies
import org.koin.android.ext.koin.androidContext

class JajankuApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initializeDependencies {
            androidContext(this@JajankuApplication)
        }
    }

}