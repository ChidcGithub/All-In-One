package com.allinone.feature.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.allinone.core.ui.components.DemoCard
import com.allinone.core.ui.components.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3ExpressiveScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("M3 Expressive") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                }
            )
        },
        floatingActionButton = {
            FABMenuDemo()
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
            SectionHeader("M3 Expressive Components")

            DemoCard(
                title = "ButtonGroup (New)",
                description = "Grouped buttons for related actions"
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    androidx.compose.material3.Button(onClick = {}) { Text("Left") }
                    androidx.compose.material3.OutlinedButton(onClick = {}) { Text("Middle") }
                    androidx.compose.material3.Button(onClick = {}) { Text("Right") }
                }
            }

            DemoCard(
                title = "SplitButton (New)",
                description = "Button with primary and secondary action areas"
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    androidx.compose.material3.Button(
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer8()
                        Text("Primary Action")
                    }
                    androidx.compose.material3.Button(
                        onClick = {},
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Options")
                    }
                }
            }

            DemoCard(
                title = "LoadingIndicator (New)",
                description = "Contained loading indicator with label"
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    LoadingIndicatorDemo()
                }
            }

            DemoCard(
                title = "VerticalFloatingToolbar (New)",
                description = "Toolbar that expands vertically from anchor"
            ) {
                VerticalFloatingToolbarDemo()
            }

            SectionHeader("Expressive Animations")

            DemoCard(
                title = "Spring Animations",
                description = "Physics-based spring motion for all interactions"
            ) {
                SpringAnimationDemo()
            }

            DemoCard(
                title = "Spring Speed Variants",
                description = "Default / Fast / Slow spring speeds"
            ) {
                SpringSpeedDemo()
            }

            SectionHeader("Expressive Shapes")

            DemoCard(
                title = "Shape Variations",
                description = "35 new shape variations in M3 Expressive"
            ) {
                ShapeVariationsDemo()
            }

            SectionHeader("Updated Components")

            DemoCard(
                title = "Enhanced Buttons",
                description = "Larger touch targets with expressive styling"
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    androidx.compose.material3.Button(onClick = {}) { Text("Filled") }
                    androidx.compose.material3.FilledTonalButton(onClick = {}) { Text("Tonal") }
                    androidx.compose.material3.OutlinedButton(onClick = {}) { Text("Outlined") }
                }
            }

            DemoCard(
                title = "Progress Indicators",
                description = "Enhanced progress with expressive colors"
            ) {
                ProgressIndicatorDemo()
            }
        }
    }
}

@Composable
fun Spacer8() = androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(8.dp))

@Composable
fun FABMenuDemo() {
    var expanded by remember { mutableStateOf(false) }

    Box {
        if (expanded) {
            Column(
                modifier = Modifier.padding(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FloatingActionButton(onClick = { expanded = false }, containerColor = MaterialTheme.colorScheme.secondary) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                FloatingActionButton(onClick = { expanded = false }, containerColor = MaterialTheme.colorScheme.tertiary) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
        FloatingActionButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}

@Composable
fun LoadingIndicatorDemo() {
    var isLoading by remember { mutableStateOf(true) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Text("Default", style = MaterialTheme.typography.labelSmall)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(trackColor = MaterialTheme.colorScheme.surfaceVariant)
                Text("Custom", style = MaterialTheme.typography.labelSmall)
            }
        }
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        androidx.compose.material3.Button(onClick = { isLoading = !isLoading }) {
            Text(if (isLoading) "Stop Loading" else "Start Loading")
        }
    }
}

@Composable
fun VerticalFloatingToolbarDemo() {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (expanded) {
                Card(onClick = { expanded = false }) {
                    Row(modifier = Modifier.padding(12.dp)) {
                        Icon(Icons.Default.Settings, contentDescription = null)
                        Text("Settings", modifier = Modifier.padding(start = 8.dp))
                    }
                }
                Card(onClick = { expanded = false }) {
                    Row(modifier = Modifier.padding(12.dp)) {
                        Icon(Icons.Default.Favorite, contentDescription = null)
                        Text("Favorite", modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
            }
        }
    }
}

@Composable
fun SpringAnimationDemo() {
    var toggled by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (toggled) 1.5f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primary)
            .then(Modifier.padding(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size((40 * scale).dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.onPrimary)
        )
    }
    androidx.compose.material3.Button(
        onClick = { toggled = !toggled },
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Text("Toggle Spring Animation")
    }
}

@Composable
fun SpringSpeedDemo() {
    var speed by remember { mutableStateOf("default") }

    val springSpec = when (speed) {
        "fast" -> spring<Float>(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessVeryLow)
        "slow" -> spring<Float>(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = 50f)
        else -> spring<Float>()
    }

    var value by remember { mutableStateOf(0f) }
    val animatedValue by animateFloatAsState(targetValue = value, animationSpec = springSpec)

    Column {
        androidx.compose.material3.Slider(
            value = animatedValue,
            onValueChange = { value = it },
            modifier = Modifier.fillMaxWidth()
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            androidx.compose.material3.Button(onClick = { speed = "default" }) { Text("Default") }
            androidx.compose.material3.Button(onClick = { speed = "fast" }) { Text("Fast") }
            androidx.compose.material3.Button(onClick = { speed = "slow" }) { Text("Slow") }
        }
    }
}

@Composable
fun ProgressIndicatorDemo() {
    var progress by remember { mutableStateOf(0.5f) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(progress = { progress })
                Text("Determinate", style = MaterialTheme.typography.labelSmall)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Text("Indeterminate", style = MaterialTheme.typography.labelSmall)
            }
        }
        androidx.compose.material3.Slider(
            value = progress,
            onValueChange = { progress = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ShapeVariationsDemo() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(MaterialTheme.colorScheme.primary)
        )
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.secondary)
        )
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.tertiary)
        )
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.primary)
        )
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.secondary)
        )
    }
}
