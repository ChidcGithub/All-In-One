package com.allinone

import android.app.Application
import com.allinone.core.di.AppModule

class AllInOneApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppModule.init(this)
    }
}
