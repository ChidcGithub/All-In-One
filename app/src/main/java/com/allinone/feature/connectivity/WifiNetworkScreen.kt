package com.allinone.feature.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
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
fun WifiNetworkScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val connectivityManager = remember { context.getSystemService(ConnectivityManager::class.java) }
    val wifiManager = remember { context.getSystemService(WifiManager::class.java) }

    var networkStatus by remember { mutableStateOf("Checking network...") }
    var wifiInfo by remember { mutableStateOf("Wi-Fi info not available") }
    val networkLog = remember { mutableStateListOf<String>() }

    // Network callback
    val networkCallback = remember {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                networkLog.add(0, "Network available")
            }

            override fun onLost(network: Network) {
                networkLog.add(0, "Network lost")
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                val capabilities = buildList {
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) add("WiFi")
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) add("Cellular")
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) add("Ethernet")
                    if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) add("Internet")
                    if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)) add("Not metered")
                }
                networkLog.add(0, "Capabilities: ${capabilities.joinToString(", ")}")
            }
        }
    }

    DisposableEffect(Unit) {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager?.registerNetworkCallback(request, networkCallback)

        onDispose {
            connectivityManager?.unregisterNetworkCallback(networkCallback)
        }
    }

    // Get current network info
    val activeNetwork = connectivityManager?.activeNetwork
    val capabilities = connectivityManager?.getNetworkCapabilities(activeNetwork)

    val isConnected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    val connectionType = when {
        capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> "Wi-Fi"
        capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> "Cellular"
        capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> "Ethernet"
        else -> "Unknown"
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Wi-Fi & Network") },
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
            SectionHeader("Network Status")

            DemoCard(
                title = "Connection Status",
                description = "Current network connectivity state"
            ) {
                Column {
                    Text(
                        text = "Connected: ${if (isConnected) "Yes" else "No"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Type: $connectionType",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            SectionHeader("Wi-Fi Information")

            DemoCard(
                title = "Wi-Fi Status",
                description = "Current Wi-Fi connection details"
            ) {
                Column {
                    Button(
                        onClick = {
                            val wifiInfo_current = wifiManager?.connectionInfo
                            wifiInfo = wifiInfo_current?.let {
                                "SSID: ${it.ssid}\nBSSID: ${it.bssid}\nLink Speed: ${it.linkSpeed} Mbps\nRSSI: ${it.rssi}"
                            } ?: "Wi-Fi not connected"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Get Wi-Fi Info")
                    }
                    Text(
                        text = wifiInfo,
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            SectionHeader("Network Monitoring")

            DemoCard(
                title = "Network Callback",
                description = "Real-time network state monitoring"
            ) {
                Text(
                    text = "NetworkCallback registered and monitoring network changes.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            DemoCard(
                title = "Socket Communication",
                description = "TCP/UDP socket operations"
            ) {
                Column {
                    Button(
                        onClick = {
                            networkLog.add(0, "TCP Socket: Connection attempt...")
                            networkLog.add(0, "Socket demo: Would connect to host:port")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("TCP Demo")
                    }
                    Button(
                        onClick = {
                            networkLog.add(0, "UDP Socket: Datagram send...")
                            networkLog.add(0, "Socket demo: Would send datagram")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("UDP Demo")
                    }
                }
            }

            SectionHeader("Network Log")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    networkLog.take(15).forEach { log ->
                        Text(
                            text = log,
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )
                    }
                    if (networkLog.isEmpty()) {
                        Text(
                            text = "Waiting for network events...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
