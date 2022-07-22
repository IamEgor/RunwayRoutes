package com.runway.routes

import android.app.Application
import com.runway.routes.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class RunwayApp : Application() {

    companion object {
        lateinit var instance: RunwayApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@RunwayApp)
            modules(appModule)
        }
    }
}