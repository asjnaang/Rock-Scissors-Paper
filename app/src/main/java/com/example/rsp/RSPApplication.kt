package com.example.rsp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RSPApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RSPApplication)
            modules(listOf(viewModelModule))
        }
    }
}