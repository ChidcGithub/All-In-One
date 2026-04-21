package com.allinone.feature.android16

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
import androidx.compose.runtime.getValue
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
fun Android16Screen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var apiCheckResult by remember { mutableStateOf("API check not performed") }

    // API 36 check using SDK_INT_FULL
    val isApi36 = Build.VERSION.SDK_INT_FULL >= 36

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Android 16 APIs") },
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
            SectionHeader("Device Info")

            DemoCard(
                title = "SDK Level",
                description = "Current device Android version"
            ) {
                Column {
                    Text(
                        text = "SDK_INT: ${Build.VERSION.SDK_INT}",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                    Text(
                        text = "RELEASE: ${Build.VERSION.RELEASE}",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                    Text(
                        text = "CODENAME: ${Build.VERSION.CODENAME}",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                    Text(
                        text = "Is Android 16+: $isApi36",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isApi36) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.error
                    )
                }
            }

            SectionHeader("Privacy Sandbox")

            DemoCard(
                title = "Privacy Sandbox APIs",
                description = "New privacy-preserving advertising APIs"
            ) {
                Column {
                    Text(
                        text = "Privacy Sandbox replaces third-party cookies with privacy-preserving APIs for attribution and interest-based advertising.",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Button(
                        onClick = {
                            apiCheckResult = "Privacy Sandbox: Requires Android 14+ with Privacy Sandbox update"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Check Privacy Sandbox")
                    }
                    Text(
                        text = apiCheckResult,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            SectionHeader("Coarse Location")

            DemoCard(
                title = "Fuzzy/Coarse Location",
                description = "Enhanced coarse location for privacy"
            ) {
                Text(
                    text = "Android 16 improves coarse location accuracy while maintaining privacy boundaries. Apps can now get approximate location without precise GPS coordinates.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            SectionHeader("Background Start Restrictions")

            DemoCard(
                title = "Background Activity Starts",
                description = "Restrictions on starting activities from background"
            ) {
                Text(
                    text = "Android 16 enforces stricter rules on background activity starts to prevent disruptive user experiences. Apps must have valid reasons to bring UI to foreground.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            SectionHeader("Foreground Service Types")

            DemoCard(
                title = "Foreground Service Type细化",
                description = "More granular foreground service types"
            ) {
                Column {
                    Text(
                        text = "Android 16 introduces additional foreground service types:\n" +
                                "- FOREGROUND_SERVICE_TYPE_CAMERA\n" +
                                "- FOREGROUND_SERVICE_TYPE_LOCATION\n" +
                                "- FOREGROUND_SERVICE_TYPE_MICROPHONE\n" +
                                "- FOREGROUND_SERVICE_TYPE_DATA_SYNC\n" +
                                "- FOREGROUND_SERVICE_TYPE_PHONE_CALL\n" +
                                "- FOREGROUND_SERVICE_TYPE_HEALTH",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
            }

            SectionHeader("SDK_INT_FULL & VERSION_CODES_FULL")

            DemoCard(
                title = "Extended Version Checking",
                description = "Use SDK_INT_FULL for precise version detection"
            ) {
                Column {
                    Button(
                        onClick = {
                            apiCheckResult = "SDK_INT_FULL: ${Build.VERSION.SDK_INT_FULL}\n" +
                                    "VERSION_CODES_FULL.BAKLAVA: 36"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Check SDK_INT_FULL")
                    }
                    Text(
                        text = apiCheckResult,
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            SectionHeader("Additional Android 16 Features")

            DemoCard(
                title = "Path Traversal Protection",
                description = "Enhanced security against path traversal attacks"
            ) {
                Text(
                    text = "Android 16 adds stricter path validation to prevent directory traversal vulnerabilities in file operations.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            DemoCard(
                title = "Photo Picker Updates",
                description = "Enhanced photo picker with more granular access"
            ) {
                Text(
                    text = "Photo picker improvements for selective media access without broad storage permissions.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            DemoCard(
                title = "Edge-to-Edge Enforcement",
                description = "Mandatory edge-to-edge rendering"
            ) {
                Text(
                    text = "Android 16 enforces edge-to-edge rendering for all apps, requiring proper handling of system bars and insets.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            SectionHeader("API Summary")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Android 16 (API 36) - Baklava\n" +
                                "Key changes: Privacy Sandbox, Coarse Location, Background Restrictions, FGS Types, Edge-to-Edge",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
            }
        }
    }
}
