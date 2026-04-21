package com.allinone.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.allinone.navigation.DemoModule
import com.allinone.navigation.ModuleCategory

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    isExpandedScreen: Boolean,
    onModuleClick: (DemoModule) -> Unit,
    modifier: Modifier = Modifier
) {
    val modules by viewModel.modules.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        // Header
        HomeHeader()

        // Search bar
        SearchBar(
            query = searchQuery,
            onQueryChange = viewModel::onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Category filter chips
        CategoryFilterRow(
            categories = viewModel.categories,
            selectedCategory = selectedCategory,
            onCategorySelected = viewModel::onCategorySelected,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Module grid
        ModuleList(
            modules = modules,
            onModuleClick = onModuleClick,
            isExpandedScreen = isExpandedScreen,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun HomeHeader() {
    Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)) {
        Text(
            text = "All in one",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Android Native API Showcase",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text("Search API modules...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search")
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear")
                }
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        shape = MaterialTheme.shapes.extraLarge
    )
}

@Composable
fun CategoryFilterRow(
    categories: List<ModuleCategory>,
    selectedCategory: ModuleCategory?,
    onCategorySelected: (ModuleCategory?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = { Text("All") }
            )
        }
        items(categories) { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = { Text(category.displayName) }
            )
        }
    }
}

@Composable
fun ModuleList(
    modules: List<DemoModule>,
    onModuleClick: (DemoModule) -> Unit,
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier
) {
    if (isExpandedScreen) {
        // Grid layout for expanded screens
        ModuleGrid(modules = modules, onModuleClick = onModuleClick, modifier = modifier)
    } else {
        // LazyColumn for normal screens
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
        ) {
            items(modules) { module ->
                ModuleCard(module = module, onClick = { onModuleClick(module) })
            }
        }
    }
}

@Composable
fun ModuleGrid(
    modules: List<DemoModule>,
    onModuleClick: (DemoModule) -> Unit,
    modifier: Modifier = Modifier
) {
    // Simplified grid - can be enhanced with adaptive library
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        items(modules.chunked(2)) { rowModules ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                rowModules.forEach { module ->
                    ModuleCard(
                        module = module,
                        onClick = { onModuleClick(module) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun ModuleCard(
    module: DemoModule,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = getIconForModule(module.icon),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = module.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = module.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun getIconForModule(iconName: String): ImageVector {
    return when (iconName) {
        "Navigation" -> Icons.AutoMirrored.Filled.ArrowBack
        "SettingsApplications", "Build" -> Icons.Default.Build
        "BroadcastOnPersonal", "Share" -> Icons.Default.Share
        "CloudSync", "Cloud" -> Icons.Default.Cloud
        "Dashboard" -> Icons.Default.Dashboard
        "Palette" -> Icons.Default.Palette
        "Animation" -> Icons.Default.AutoAwesome
        "Brush" -> Icons.Default.Brush
        "Sensors", "Devices" -> Icons.Default.Devices
        "Multimedia", "PlayCircle" -> Icons.Default.PlayCircle
        "MyLocation" -> Icons.Default.MyLocation
        "Bluetooth" -> Icons.Default.Bluetooth
        "Wifi" -> Icons.Default.Wifi
        "Storage" -> Icons.Default.Storage
        "Https", "Lock" -> Icons.Default.Lock
        "Notifications" -> Icons.Default.Notifications
        "Schedule" -> Icons.Default.Schedule
        "Security" -> Icons.Default.Lock
        "Accessibility" -> Icons.Default.Accessibility
        "NewReleases" -> Icons.Default.NewReleases
        else -> Icons.Default.Dashboard
    }
}
