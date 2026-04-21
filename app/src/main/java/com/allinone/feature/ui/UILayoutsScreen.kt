package com.allinone.feature.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.allinone.core.ui.components.DemoCard
import com.allinone.core.ui.components.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UILayoutsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("UI & Layouts") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { SectionHeader("Row Layout") }
            item {
                DemoCard(
                    title = "Row - Horizontal Arrangement",
                    description = "Arranges children horizontally"
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.size(40.dp).background(MaterialTheme.colorScheme.primary))
                        Box(Modifier.size(40.dp).background(MaterialTheme.colorScheme.secondary))
                        Box(Modifier.size(40.dp).background(MaterialTheme.colorScheme.tertiary))
                    }
                }
            }

            item { SectionHeader("Column Layout") }
            item {
                DemoCard(
                    title = "Column - Vertical Arrangement",
                    description = "Arranges children vertically"
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(Modifier.fillMaxWidth().height(30.dp).background(MaterialTheme.colorScheme.primary))
                        Box(Modifier.fillMaxWidth().height(30.dp).background(MaterialTheme.colorScheme.secondary))
                        Box(Modifier.fillMaxWidth().height(30.dp).background(MaterialTheme.colorScheme.tertiary))
                    }
                }
            }

            item { SectionHeader("Box Layout") }
            item {
                DemoCard(
                    title = "Box - Stacking Children",
                    description = "Stacks children on top of each other"
                ) {
                    Box(
                        modifier = Modifier.size(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                        )
                        Box(
                            Modifier
                                .size(80.dp)
                                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f))
                        )
                        Box(
                            Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.tertiary)
                        )
                    }
                }
            }

            item { SectionHeader("LazyColumn") }
            item {
                DemoCard(
                    title = "LazyColumn - Efficient List",
                    description = "Only renders visible items for performance"
                ) {
                    LazyColumn(
                        modifier = Modifier.height(200.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(20) { index ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Item $index")
                                Icon(Icons.Default.Star, contentDescription = null, Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }

            item { SectionHeader("LazyRow") }
            item {
                DemoCard(
                    title = "LazyRow - Horizontal List",
                    description = "Horizontal scrolling list"
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        itemsIndexed(0..9) { _, index ->
                            Card(
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(index.toString())
                                }
                            }
                        }
                    }
                }
            }

            item { SectionHeader("Scrollable") }
            item {
                DemoCard(
                    title = "Vertical Scroll",
                    description = "Traditional scrollable view"
                ) {
                    Column(
                        modifier = Modifier
                            .height(200.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        repeat(20) { index ->
                            Text(
                                "Line ${index + 1}",
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            item { SectionHeader("Tabs") }
            item { TabDemo() }

            item { SectionHeader("Buttons Demo") }
            item { ButtonDemo() }

            item { SectionHeader("Sliders Demo") }
            item { SliderDemo() }

            item { SectionHeader("Animated Content") }
            item { AnimatedContentDemo() }
        }
    }
}

@Composable
fun TabDemo() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Tab 1", "Tab 2", "Tab 3")

    Card(modifier = Modifier.fillMaxWidth()) {
        Column {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Content for ${tabs[selectedTab]}")
            }
        }
    }
}

@Composable
fun ButtonDemo() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Primary Button")
            }
            Spacer(Modifier.height(8.dp))
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Outlined Button")
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Text Button")
            }
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Favorite, contentDescription = "Favorite")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Star, contentDescription = "Star")
                }
            }
        }
    }
}

@Composable
fun SliderDemo() {
    var sliderValue by remember { mutableStateOf(0.5f) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Value: ${"%.2f".format(sliderValue)}")
            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AnimatedContentDemo() {
    var expanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.secondary
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .animateContentSize()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(if (expanded) "Tap to collapse" else "Tap to expand")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (expanded) 100.dp else 40.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(color)
            )
        }
    }
}
