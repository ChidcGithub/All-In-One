package com.allinone.feature.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.allinone.core.ui.components.DemoCard
import com.allinone.core.ui.components.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimationsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Animations") },
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
            SectionHeader("Visibility Animations")

            DemoCard(title = "AnimatedVisibility", description = "Fade in/out with expand/shrink") {
                AnimatedVisibilityDemo()
            }

            SectionHeader("Value Animations")

            DemoCard(title = "Infinite Transition", description = "Continuous rotation animation") {
                InfiniteTransitionDemo()
            }

            SectionHeader("Gesture-Driven Animations")

            DemoCard(title = "Drag Animation", description = "Animation driven by gesture offset") {
                GestureDrivenAnimationDemo()
            }

            SectionHeader("Spring Animations")

            DemoCard(title = "Bouncy Spring", description = "Physics-based spring motion") {
                BouncySpringDemo()
            }

            SectionHeader("Path Animations")

            DemoCard(title = "Circular Path", description = "Object moving along circular path") {
                PathAnimationDemo()
            }
        }
    }
}

@Composable
fun AnimatedVisibilityDemo() {
    var visible by remember { mutableStateOf(true) }

    Column {
        Button(onClick = { visible = !visible }, modifier = Modifier.fillMaxWidth()) {
            Text(if (visible) "Hide" else "Show")
        }
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Composable
fun InfiniteTransitionDemo() {
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .rotate(rotation),
                tint = MaterialTheme.colorScheme.primary
            )
            Icon(
                Icons.Default.Favorite,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .rotate(rotation * 0.7f),
                tint = MaterialTheme.colorScheme.secondary
            )
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .rotate(rotation * 1.3f),
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
fun GestureDrivenAnimationDemo() {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var boxWidth by remember { mutableFloatStateOf(0f) }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .onSizeChanged { boxWidth = it.width.toFloat() }
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .offset { IntOffset(offsetX.toInt(), 0) }
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
        Slider(
            value = offsetX,
            onValueChange = { offsetX = it },
            valueRange = 0f..(boxWidth - 60),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BouncySpringDemo() {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(if (expanded) 100.dp else 50.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.primary)
        )
    }
    Button(onClick = { expanded = !expanded }, modifier = Modifier.fillMaxWidth()) {
        Text("Toggle Spring")
    }
}

@Composable
fun PathAnimationDemo() {
    val infiniteTransition = rememberInfiniteTransition(label = "circular")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle"
    )

    val radius = 60.dp
    val x = radius.value * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
    val y = radius.value * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .offset { IntOffset(x.toInt(), y.toInt()) }
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}
