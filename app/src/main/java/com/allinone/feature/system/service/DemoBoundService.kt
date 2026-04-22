package com.allinone.feature.system.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

/**
 * Demo bound service that exposes data via IBinder.
 */
class DemoBoundService : Service() {

    private val binder = LocalBinder()
    private var data = "DemoBoundService initialized"

    inner class LocalBinder : Binder() {
        fun getService(): DemoBoundService = this@DemoBoundService
    }

    override fun onBind(intent: Intent): IBinder = binder

    fun getData(): String = data

    fun updateData(newData: String) {
        data = newData
    }
}
