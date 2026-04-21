package com.allinone.feature.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
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
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BroadcastScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var broadcastLog by remember { mutableStateOf(listOf<String>()) }
    var isReceiverRegistered by remember { mutableStateOf(false) }

    val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    val receiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val action = intent?.action ?: "Unknown"
                val time = dateFormat.format(System.currentTimeMillis())
                broadcastLog = broadcastLog + "[$time] Received: $action"
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            if (isReceiverRegistered) {
                context.unregisterReceiver(receiver)
            }
        }
    }

    fun registerDynamicReceiver() {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_CHANGED)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED)
        } else {
            context.registerReceiver(receiver, filter)
        }
        isReceiverRegistered = true
        broadcastLog = broadcastLog + "Dynamic receiver registered"
    }

    fun unregisterDynamicReceiver() {
        if (isReceiverRegistered) {
            context.unregisterReceiver(receiver)
            isReceiverRegistered = false
            broadcastLog = broadcastLog + "Dynamic receiver unregistered"
        }
    }

    fun sendCustomBroadcast() {
        val intent = Intent("com.allinone.CUSTOM_ACTION").apply {
            putExtra("message", "Custom broadcast at ${dateFormat.format(System.currentTimeMillis())}")
        }
        context.sendBroadcast(intent)
        broadcastLog = broadcastLog + "Custom broadcast sent"
    }

    fun sendOrderedBroadcast() {
        val intent = Intent("com.allinone.ORDERED_ACTION")
        context.sendOrderedBroadcast(intent, null)
        broadcastLog = broadcastLog + "Ordered broadcast sent"
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("BroadcastReceiver") },
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
            SectionHeader("Dynamic Broadcast Registration")

            DemoCard(
                title = "Register/Unregister Receiver",
                description = "Register receiver at runtime for system events"
            ) {
                Column {
                    Button(
                        onClick = {
                            if (!isReceiverRegistered) registerDynamicReceiver()
                            else unregisterDynamicReceiver()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (isReceiverRegistered) "Unregister Receiver" else "Register Receiver")
                    }
                    Text(
                        text = "Status: ${if (isReceiverRegistered) "Registered" else "Not registered"}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            SectionHeader("Send Broadcasts")

            DemoCard(
                title = "Normal Broadcast",
                description = "Send a standard broadcast to all receivers"
            ) {
                Button(onClick = { sendCustomBroadcast() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Send Custom Broadcast")
                }
            }

            DemoCard(
                title = "Ordered Broadcast",
                description = "Send broadcast with priority ordering"
            ) {
                Button(onClick = { sendOrderedBroadcast() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Send Ordered Broadcast")
                }
            }

            SectionHeader("Static Broadcast Receivers")

            DemoCard(
                title = "Boot Completed",
                description = "Registered in AndroidManifest for BOOT_COMPLETED"
            ) {
                Text(
                    text = "This receiver is statically registered and will receive boot events automatically.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            DemoCard(
                title = "Battery Events",
                description = "Listens for BATTERY_LOW, BATTERY_OKAY, POWER events"
            ) {
                Text(
                    text = "Static receiver registered for battery and power events.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            SectionHeader("Broadcast Log")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    broadcastLog.takeLast(20).forEach { log ->
                        Text(
                            text = log,
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )
                    }
                    if (broadcastLog.isEmpty()) {
                        Text(
                            text = "No broadcasts received yet",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
