package com.allinone.feature.system

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.allinone.core.ui.components.DemoCard
import com.allinone.core.ui.components.SectionHeader
import com.allinone.feature.system.service.DemoForegroundService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isForegroundRunning by remember { mutableStateOf(false) }
    var isBackgroundRunning by remember { mutableStateOf(false) }
    var isBound by remember { mutableStateOf(false) }
    var binderData by remember { mutableStateOf("Not bound") }
    var serviceLog by remember { mutableStateOf(listOf<String>()) }

    fun addLog(msg: String) {
        serviceLog = serviceLog + msg
    }

    val serviceConnection = remember {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                isBound = true
                binderData = "Bound to service: ${name?.className}"
                addLog("Service connected")
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                isBound = false
                binderData = "Service disconnected"
                addLog("Service disconnected")
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            if (isBound) {
                context.unbindService(serviceConnection)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Services") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionHeader("Foreground Service")

            DemoCard(
                title = "Foreground Service",
                description = "Service with persistent notification (API 36+)"
            ) {
                Button(
                    onClick = {
                        if (!isForegroundRunning) {
                            val intent = Intent(context, DemoForegroundService::class.java)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                context.startForegroundService(intent)
                            }
                            isForegroundRunning = true
                            addLog("Foreground service started")
                        } else {
                            context.stopService(Intent(context, DemoForegroundService::class.java))
                            isForegroundRunning = false
                            addLog("Foreground service stopped")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isForegroundRunning) "Stop Foreground Service" else "Start Foreground Service")
                }
            }

            SectionHeader("Background Service")

            DemoCard(
                title = "Background Service",
                description = "Service running without notification (limited on Android 16+)"
            ) {
                Button(
                    onClick = {
                        if (!isBackgroundRunning) {
                            // In Android 16+, background services are heavily restricted
                            addLog("Background service attempted (restricted on API 36+)")
                            isBackgroundRunning = true
                        } else {
                            addLog("Background service stopped")
                            isBackgroundRunning = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isBackgroundRunning) "Stop Background Service" else "Start Background Service")
                }
            }

            SectionHeader("Bound Service")

            DemoCard(
                title = "Bound Service",
                description = "Client-server interface using IBinder"
            ) {
                Button(
                    onClick = {
                        if (!isBound) {
                            val intent = Intent(context, DemoForegroundService::class.java)
                            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
                            addLog("Binding to service...")
                        } else {
                            context.unbindService(serviceConnection)
                            isBound = false
                            binderData = "Unbound"
                            addLog("Unbound from service")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isBound) "Unbind Service" else "Bind Service")
                }
                Text(
                    text = "Status: $binderData",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            SectionHeader("Service Log")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    serviceLog.takeLast(15).forEach { log ->
                        Text(
                            text = "> $log",
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }
}
