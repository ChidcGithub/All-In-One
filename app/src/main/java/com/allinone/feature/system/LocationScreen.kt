package com.allinone.feature.system

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.core.content.ContextCompat
import com.allinone.core.ui.components.DemoCard
import com.allinone.core.ui.components.SectionHeader
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var locationStatus by remember { mutableStateOf("Location not requested") }
    var lastKnownLocation by remember { mutableStateOf<Location?>(null) }
    var isTracking by remember { mutableStateOf(false) }

    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val allGranted = locationPermissionState.allPermissionsGranted

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Location") },
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
            SectionHeader("Location Permissions")

            DemoCard(
                title = "Request Permissions",
                description = "ACCESS_FINE_LOCATION & ACCESS_COARSE_LOCATION"
            ) {
                Button(
                    onClick = { locationPermissionState.launchMultiplePermissionRequest() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Request Location Permission")
                }
                Text(
                    text = "Status: ${if (allGranted) "Granted" else "Not granted"}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            SectionHeader("Location Providers")

            DemoCard(
                title = "GPS Location",
                description = "High accuracy using GPS satellite"
            ) {
                Column {
                    Button(
                        onClick = {
                            if (allGranted) {
                                locationStatus = "Fetching GPS location..."
                                // In real app: FusedLocationProviderClient.getLastLocation()
                                locationStatus = "GPS: Lat 37.7749, Lng -122.4194 (demo)"
                            } else {
                                locationStatus = "Permission denied"
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Get GPS Location")
                    }
                    Text(
                        text = locationStatus,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            DemoCard(
                title = "Network Location",
                description = "Approximate location using cell towers and Wi-Fi"
            ) {
                Button(
                    onClick = {
                        locationStatus = "Network: Lat 37.775, Lng -122.419 (demo)"
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Get Network Location")
                }
            }

            SectionHeader("Location Updates")

            DemoCard(
                title = "Continuous Tracking",
                description = "Receive location updates at regular intervals"
            ) {
                Column {
                    Button(
                        onClick = {
                            isTracking = !isTracking
                            locationStatus = if (isTracking) "Tracking started..." else "Tracking stopped"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (isTracking) "Stop Tracking" else "Start Tracking")
                    }
                    if (isTracking) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    }
                    Text(
                        text = locationStatus,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            SectionHeader("Geofencing")

            DemoCard(
                title = "Geofence Setup",
                description = "Create virtual boundaries and receive alerts"
            ) {
                Button(
                    onClick = { locationStatus = "Geofence created at current location" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create Geofence")
                }
            }

            SectionHeader("Coarse Location (Android 16)")

            DemoCard(
                title = "Fuzzy Location",
                description = "Android 16+ approximate location API"
            ) {
                Text(
                    text = "Uses new COARSE_LOCATION optimization for privacy",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            SectionHeader("Last Known Location")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = locationStatus,
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
            }
        }
    }
}
