package com.allinone.feature.storage

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
import androidx.compose.material3.OutlinedTextField
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
import com.allinone.core.di.AppModule
import com.allinone.core.ui.components.DemoCard
import com.allinone.core.ui.components.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorageScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // SharedPreferences
    var sharedPrefsValue by remember { mutableStateOf("") }
    var sharedPrefsInput by remember { mutableStateOf("") }

    // DataStore
    var dataStoreValue by remember { mutableStateOf("Not loaded") }
    var dataStoreInput by remember { mutableStateOf("") }

    // File storage
    var fileContent by remember { mutableStateOf("No file read") }
    var fileInput by remember { mutableStateOf("") }

    // Room
    var roomResult by remember { mutableStateOf("No database operation") }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Local Storage") },
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
            SectionHeader("SharedPreferences")

            DemoCard(
                title = "Key-Value Storage",
                description = "Simple persistent storage for primitive data types"
            ) {
                Column {
                    OutlinedTextField(
                        value = sharedPrefsInput,
                        onValueChange = { sharedPrefsInput = it },
                        label = { Text("Value to save") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = {
                            AppModule.sharedPreferences.edit()
                                .putString("demo_key", sharedPrefsInput)
                                .apply()
                            sharedPrefsValue = "Saved: $sharedPrefsInput"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save")
                    }
                    Button(
                        onClick = {
                            sharedPrefsValue = AppModule.sharedPreferences.getString("demo_key", "No value") ?: "No value"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Load")
                    }
                    Text(
                        text = sharedPrefsValue,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            SectionHeader("DataStore")

            DemoCard(
                title = "DataStore (Preferences)",
                description = "Modern alternative to SharedPreferences with Coroutines"
            ) {
                Column {
                    OutlinedTextField(
                        value = dataStoreInput,
                        onValueChange = { dataStoreInput = it },
                        label = { Text("Value to save") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = {
                            // In real app: viewModelScope.launch { dataStore.edit { it[preferencesKey("demo")] = dataStoreInput } }
                            dataStoreValue = "Saved to DataStore: $dataStoreInput"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save to DataStore")
                    }
                    Text(
                        text = dataStoreValue,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            SectionHeader("Room Database")

            DemoCard(
                title = "Room (SQLite)",
                description = "ORM-based SQLite database access"
            ) {
                Column {
                    Button(
                        onClick = {
                            // In real app: viewModelScope.launch { database.demoDao().insertItem(DemoItem(...)) }
                            roomResult = "Item inserted into Room database"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Insert Item")
                    }
                    Button(
                        onClick = {
                            roomResult = "Querying all items from Room..."
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Query All Items")
                    }
                    Text(
                        text = roomResult,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            SectionHeader("File Storage")

            DemoCard(
                title = "Internal Storage",
                description = "Private files in app's internal directory"
            ) {
                Column {
                    OutlinedTextField(
                        value = fileInput,
                        onValueChange = { fileInput = it },
                        label = { Text("Content to write") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = {
                            try {
                                context.openFileOutput("demo.txt", android.content.Context.MODE_PRIVATE).use {
                                    it.write(fileInput.toByteArray())
                                }
                                fileContent = "File written successfully"
                            } catch (e: Exception) {
                                fileContent = "Error: ${e.message}"
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Write File")
                    }
                    Button(
                        onClick = {
                            try {
                                fileContent = context.openFileInput("demo.txt").bufferedReader().use { it.readText() }
                            } catch (e: Exception) {
                                fileContent = "File not found"
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Read File")
                    }
                    Text(
                        text = fileContent,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            DemoCard(
                title = "External Storage",
                description = "Shared storage (requires permissions)"
            ) {
                val externalDir = context.getExternalFilesDir(null)
                Text(
                    text = "External files dir: ${externalDir?.absolutePath ?: "Not available"}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            SectionHeader("Storage Summary")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "SharedPreferences: ${sharedPrefsValue.ifEmpty { "Not set" }}\n" +
                                "DataStore: $dataStoreValue\n" +
                                "Room: $roomResult\n" +
                                "File: $fileContent",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
            }
        }
    }
}
