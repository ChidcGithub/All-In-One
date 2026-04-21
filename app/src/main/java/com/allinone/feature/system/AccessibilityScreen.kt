package com.allinone.feature.system

import android.view.accessibility.AccessibilityManager
import android.view.accessibility.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.allinone.core.ui.components.DemoCard
import com.allinone.core.ui.components.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessibilityScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var accessibilityStatus by remember { mutableStateOf("Accessibility info not loaded") }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Accessibility") },
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
            SectionHeader("Accessibility Services")

            DemoCard(
                title = "TalkBack",
                description = "Android's screen reader"
            ) {
                Button(
                    onClick = {
                        val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
                        accessibilityStatus = "TalkBack enabled: ${am.isTouchExplorationEnabled}"
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Check TalkBack Status")
                }
            }

            DemoCard(
                title = "Accessibility Manager",
                description = "Check enabled accessibility services"
            ) {
                Column {
                    Button(
                        onClick = {
                            val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
                            val services = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
                            val serviceList = services?.joinToString("\n") { s -> s.resolveInfo.loadLabel(context.packageManager).toString() }
                            accessibilityStatus = "Enabled services: ${services?.size ?: 0}\n$serviceList"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Check Services")
                    }
                    Text(
                        text = accessibilityStatus,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            SectionHeader("Accessibility Settings")

            DemoCard(
                title = "Open Accessibility Settings",
                description = "Navigate to system accessibility settings"
            ) {
                Button(
                    onClick = {
                        val intent = android.content.Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Open Accessibility Settings")
                }
            }

            SectionHeader("Accessibility Best Practices")

            DemoCard(
                title = "Content Descriptions",
                description = "All interactive elements should have contentDescription"
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentDescription = "Demo button with accessibility description" }
                ) {
                    Text("Button with ContentDescription")
                }
            }

            DemoCard(
                title = "Touch Target Size",
                description = "Minimum 48dp touch target for accessibility"
            ) {
                Text(
                    text = "All interactive elements in this app use minimum 48dp touch targets as per Material Design guidelines.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            DemoCard(
                title = "Contrast Ratio",
                description = "Sufficient color contrast for readability"
            ) {
                Text(
                    text = "M3 Expressive ensures minimum 4.5:1 contrast ratio for normal text and 3:1 for large text.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            DemoCard(
                title = "Font Scaling",
                description = "Support system font size preferences"
            ) {
                Text(
                    text = "This app respects system font scale settings. Try changing font size in system settings.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            SectionHeader("AccessibilityNodeInfo")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Accessibility services can:\n" +
                                "- Read screen content\n" +
                                "- Perform actions on behalf of user\n" +
                                "- Navigate the UI hierarchy\n" +
                                "- Modify accessibility node properties",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
