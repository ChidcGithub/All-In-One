package com.allinone.feature.connectivity

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Nfc
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.allinone.core.ui.components.DemoCard
import com.allinone.core.ui.components.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BluetoothNfcScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val bluetoothManager = remember { context.getSystemService(BluetoothManager::class.java) }
    val bluetoothAdapter = bluetoothManager?.adapter

    var bluetoothStatus by remember { mutableStateOf("Bluetooth not initialized") }
    var nfcStatus by remember { mutableStateOf("NFC not initialized") }
    val scannedDevices = remember { mutableStateListOf<String>() }

    val isBleSupported = bluetoothAdapter != null
    val isBluetoothEnabled = bluetoothAdapter?.isEnabled == true

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Bluetooth & NFC") },
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
            SectionHeader("Bluetooth Low Energy (BLE)")

            DemoCard(
                title = "BLE Support Check",
                description = "Check if device supports Bluetooth LE"
            ) {
                Text(
                    text = if (isBleSupported) "Bluetooth LE supported" else "Bluetooth LE not supported",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Enabled: ${if (isBluetoothEnabled) "Yes" else "No"}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            DemoCard(
                title = "Enable Bluetooth",
                description = "Request user to enable Bluetooth"
            ) {
                Button(
                    onClick = {
                        if (isBleSupported && !isBluetoothEnabled) {
                            bluetoothAdapter?.enable()
                            bluetoothStatus = "Bluetooth enabled"
                        } else {
                            bluetoothStatus = "Cannot enable Bluetooth"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Enable Bluetooth")
                }
            }

            DemoCard(
                title = "BLE Scan",
                description = "Scan for nearby BLE devices"
            ) {
                Column {
                    Button(
                        onClick = {
                            scannedDevices.clear()
                            scannedDevices.add("Scanning...")
                            bluetoothStatus = "BLE scan started"
                            // Simulated scan results
                            scannedDevices.clear()
                            scannedDevices.addAll(
                                listOf(
                                    "Device A1:B2:C3:D4:E5:F6 (RSSI: -45)",
                                    "Device AA:BB:CC:DD:EE:FF (RSSI: -67)",
                                    "Device 11:22:33:44:55:66 (RSSI: -82)"
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Scan for Devices")
                    }
                    if (scannedDevices.isNotEmpty()) {
                        scannedDevices.forEach { device ->
                            Text(
                                text = "- $device",
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                            )
                        }
                    }
                }
            }

            DemoCard(
                title = "BLE Connect & Communicate",
                description = "Connect to GATT server and read/write characteristics"
            ) {
                Button(
                    onClick = { bluetoothStatus = "Connected to GATT server" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Connect to Device")
                }
            }

            SectionHeader("NFC")

            DemoCard(
                title = "NFC Availability",
                description = "Check if NFC is available on device"
            ) {
                val nfcAdapter = android.nfc.NfcAdapter.getDefaultAdapter(context)
                val nfcAvailable = nfcAdapter != null && nfcAdapter.isEnabled
                Text(
                    text = if (nfcAvailable) "NFC available" else "NFC not available",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            DemoCard(
                title = "NFC Tag Read",
                description = "Read NDEF messages from NFC tags"
            ) {
                Button(
                    onClick = { nfcStatus = "Tap an NFC tag to read" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ready to Read Tag")
                }
            }

            DemoCard(
                title = "NFC Tag Write",
                description = "Write NDEF messages to NFC tags"
            ) {
                Button(
                    onClick = { nfcStatus = "Tap tag to write message" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Write to Tag")
                }
            }

            DemoCard(
                title = "Android Beam (Deprecated)",
                description = "Peer-to-peer NFC data transfer"
            ) {
                Text(
                    text = "Android Beam was deprecated in Android 10+. Use Nearby Connections API instead.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            SectionHeader("Status")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Bluetooth: $bluetoothStatus\nNFC: $nfcStatus",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
            }
        }
    }
}
