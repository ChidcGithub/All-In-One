package com.allinone.feature.system

import android.net.Uri
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
fun ContentProviderScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var queryResult by remember { mutableStateOf("No query executed") }
    var insertResult by remember { mutableStateOf("No insert executed") }

    val providerUri = "content://com.allinone.demo.provider"

    fun queryProvider() {
        try {
            val uri = Uri.parse(providerUri)
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                val columnNames = it.columnNames.joinToString(", ")
                val count = it.count
                queryResult = "Columns: $columnNames\nRow count: $count"
            } ?: run {
                queryResult = "Query returned null cursor"
            }
        } catch (e: Exception) {
            queryResult = "Error: ${e.message}"
        }
    }

    fun insertIntoProvider() {
        try {
            val uri = Uri.parse("$providerUri/items")
            val values = android.content.ContentValues().apply {
                put("title", "Demo Item")
                put("description", "Inserted via ContentProvider")
                put("category", "demo")
            }
            val resultUri = context.contentResolver.insert(uri, values)
            insertResult = "Inserted at: $resultUri"
        } catch (e: Exception) {
            insertResult = "Error: ${e.message}"
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("ContentProvider") },
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
            SectionHeader("ContentProvider Basics")

            DemoCard(
                title = "Provider URI",
                description = "content://com.allinone.demo.provider"
            ) {
                Text(
                    text = providerUri,
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }

            SectionHeader("CRUD Operations")

            DemoCard(
                title = "Query",
                description = "Query all items from the content provider"
            ) {
                Column {
                    Button(onClick = { queryProvider() }, modifier = Modifier.fillMaxWidth()) {
                        Text("Execute Query")
                    }
                    Text(
                        text = queryResult,
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            DemoCard(
                title = "Insert",
                description = "Insert a new item into the content provider"
            ) {
                Column {
                    Button(onClick = { insertIntoProvider() }, modifier = Modifier.fillMaxWidth()) {
                        Text("Execute Insert")
                    }
                    Text(
                        text = insertResult,
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            DemoCard(
                title = "Update",
                description = "Update existing items in the content provider"
            ) {
                Button(
                    onClick = {
                        try {
                            val uri = Uri.parse("$providerUri/items/1")
                            val values = android.content.ContentValues().apply {
                                put("title", "Updated Item")
                            }
                            val count = context.contentResolver.update(uri, values, null, null)
                            queryResult = "Updated $count rows"
                        } catch (e: Exception) {
                            queryResult = "Error: ${e.message}"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Execute Update")
                }
            }

            DemoCard(
                title = "Delete",
                description = "Delete items from the content provider"
            ) {
                Button(
                    onClick = {
                        try {
                            val uri = Uri.parse("$providerUri/items/1")
                            val count = context.contentResolver.delete(uri, null, null)
                            queryResult = "Deleted $count rows"
                        } catch (e: Exception) {
                            queryResult = "Error: ${e.message}"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Execute Delete")
                }
            }

            SectionHeader("System ContentProviders")

            DemoCard(
                title = "Contacts Provider",
                description = "Access system contacts via content://contacts/"
            ) {
                Button(
                    onClick = {
                        try {
                            val uri = android.provider.ContactsContract.Contacts.CONTENT_URI
                            val cursor = context.contentResolver.query(uri, null, null, null, null)
                            cursor?.use {
                                queryResult = "Contacts count: ${it.count}"
                            }
                        } catch (e: Exception) {
                            queryResult = "Error: ${e.message}"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Query Contacts")
                }
            }

            DemoCard(
                title = "MediaStore",
                description = "Access system media files via MediaStore"
            ) {
                Button(
                    onClick = {
                        try {
                            val uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            val cursor = context.contentResolver.query(uri, null, null, null, null)
                            cursor?.use {
                                queryResult = "Images count: ${it.count}"
                            }
                        } catch (e: Exception) {
                            queryResult = "Error: ${e.message}"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Query MediaStore")
                }
            }

            SectionHeader("Query Results")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = queryResult,
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
            }
        }
    }
}
