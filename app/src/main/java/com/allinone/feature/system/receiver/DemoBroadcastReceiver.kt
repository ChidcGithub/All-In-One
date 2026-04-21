package com.allinone.feature.system.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class DemoBroadcastReceiver : BroadcastReceiver() {

    private val TAG = "DemoBroadcastReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.d(TAG, "Received broadcast: $action")

        when (action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                Log.d(TAG, "Device boot completed")
            }
            Intent.ACTION_BATTERY_LOW -> {
                Log.d(TAG, "Battery is low")
            }
            Intent.ACTION_BATTERY_OKAY -> {
                Log.d(TAG, "Battery is okay")
            }
            Intent.ACTION_POWER_CONNECTED -> {
                Log.d(TAG, "Power connected")
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                Log.d(TAG, "Power disconnected")
            }
        }
    }
}
