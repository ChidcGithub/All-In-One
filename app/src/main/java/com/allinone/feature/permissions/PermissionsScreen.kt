package com.allinone.feature.permissions

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.allinone.core.ui.components.DemoCard
import com.allinone.core.ui.components.SectionHeader
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun PermissionsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Runtime permissions
    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)
    val locationPermission = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    val notificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
    } else null
    val recordAudioPermission = rememberPermissionState(android.Manifest.permission.RECORD_AUDIO)

    var settingsPermissionResult by remember { mutableStateOf("Not checked") }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Permissions") },
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
            SectionHeader("Runtime Permissions")

            DemoCard(
                title = "Camera Permission",
                description = android.Manifest.permission.CAMERA
            ) {
                Column {
                    Text(
                        text = "Granted: ${cameraPermission.status.isGranted}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Button(
                        onClick = { cameraPermission.launchPermissionRequest() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Request Camera Permission")
                    }
                }
            }

            DemoCard(
                title = "Location Permission",
                description = android.Manifest.permission.ACCESS_FINE_LOCATION
            ) {
                Column {
                    Text(
                        text = "Granted: ${locationPermission.status.isGranted}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Button(
                        onClick = { locationPermission.launchPermissionRequest() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Request Location Permission")
                    }
                }
            }

            DemoCard(
                title = "Microphone Permission",
                description = android.Manifest.permission.RECORD_AUDIO
            ) {
                Column {
                    Text(
                        text = "Granted: ${recordAudioPermission.status.isGranted}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Button(
                        onClick = { recordAudioPermission.launchPermissionRequest() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Request Microphone Permission")
                    }
                }
            }

            if (notificationPermission != null) {
                DemoCard(
                    title = "Notification Permission (Android 13+)",
                    description = android.Manifest.permission.POST_NOTIFICATIONS
                ) {
                    Column {
                        Text(
                            text = "Granted: ${notificationPermission.status.isGranted}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Button(
                            onClick = { notificationPermission.launchPermissionRequest() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Request Notification Permission")
                        }
                    }
                }
            }

            SectionHeader("Special Permissions")

            DemoCard(
                title = "System Settings",
                description = "Request permission to modify system settings"
            ) {
                Column {
                    Button(
                        onClick = {
                            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                            context.startActivity(intent)
                            settingsPermissionResult = "Settings screen opened"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Open Settings Permission")
                    }
                    Text(
                        text = settingsPermissionResult,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            DemoCard(
                title = "Draw Over Other Apps",
                description = "SYSTEM_ALERT_WINDOW permission"
            ) {
                Button(
                    onClick = {
                        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                        intent.data = Uri.parse("package:${context.packageName}")
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Open Overlay Permission")
                }
            }

            DemoCard(
                title = "Battery Optimization",
                description = "REQUEST_IGNORE_BATTERY_OPTIMIZATIONS"
            ) {
                Button(
                    onClick = {
                        val intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Open Battery Optimization")
                }
            }

            SectionHeader("Permission Rationale")

            DemoCard(
                title = "Should Show Rationale",
                description = "Check if permission rationale should be shown"
            ) {
                Text(
                    text = "Camera rationale: ${cameraPermission.status.shouldShowRationale}\n" +
                            "Location rationale: ${locationPermission.status.shouldShowRationale}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            SectionHeader("Permission Groups")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Normal permissions are automatically granted.\n" +
                                "Dangerous permissions require runtime request.\n" +
                                "Special permissions require system settings.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
