package com.allinone.feature.system

import android.content.Intent
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
fun NavigationScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var lifecycleLog by remember { mutableStateOf(listOf("Activity created")) }

    fun addLog(message: String) {
        lifecycleLog = lifecycleLog + message
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Activity & Navigation") },
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
            SectionHeader("Activity Launch Modes")

            DemoCard(
                title = "Standard Mode",
                description = "Default launch mode - new instance each time"
            ) {
                Button(onClick = { addLog("Launched activity in STANDARD mode") }) {
                    Text("Launch Standard")
                }
            }

            DemoCard(
                title = "SingleTop Mode",
                description = "Reuses existing instance if at top of stack"
            ) {
                Button(onClick = { addLog("Launched activity in SINGLE_TOP mode") }) {
                    Text("Launch SingleTop")
                }
            }

            DemoCard(
                title = "SingleTask Mode",
                description = "Creates new task and activity root"
            ) {
                Button(onClick = { addLog("Launched activity in SINGLE_TASK mode") }) {
                    Text("Launch SingleTask")
                }
            }

            DemoCard(
                title = "SingleInstance Mode",
                description = "Isolated task with single activity"
            ) {
                Button(onClick = { addLog("Launched activity in SINGLE_INSTANCE mode") }) {
                    Text("Launch SingleInstance")
                }
            }

            SectionHeader("Intent Flags Demo")

            DemoCard(
                title = "Clear Top",
                description = "FLAG_ACTIVITY_CLEAR_TOP - clears activities above"
            ) {
                Button(onClick = { addLog("Intent with FLAG_ACTIVITY_CLEAR_TOP") }) {
                    Text("Test Clear Top")
                }
            }

            DemoCard(
                title = "New Task",
                description = "FLAG_ACTIVITY_NEW_TASK - starts new task"
            ) {
                Button(onClick = { addLog("Intent with FLAG_ACTIVITY_NEW_TASK") }) {
                    Text("Test New Task")
                }
            }

            SectionHeader("Compose Navigation")

            DemoCard(
                title = "Type-Safe Navigation",
                description = "Using Kotlinx serialization for route types"
            ) {
                Button(onClick = { addLog("Navigate using type-safe route") }) {
                    Text("Navigate Type-Safe")
                }
            }

            DemoCard(
                title = "Nested Navigation Graphs",
                description = "Hierarchical navigation with sub-graphs"
            ) {
                Button(onClick = { addLog("Navigate to nested graph") }) {
                    Text("Nested Graph")
                }
            }

            SectionHeader("Lifecycle Events Log")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    lifecycleLog.takeLast(15).forEach { log ->
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
