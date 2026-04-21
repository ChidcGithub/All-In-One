package com.allinone.feature.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.allinone.core.ui.components.DemoCard
import com.allinone.core.ui.components.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDrawingScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Custom Drawing") },
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
            SectionHeader("Basic Shapes")

            DemoCard(title = "Canvas Shapes", description = "Drawing basic shapes with Canvas") {
                BasicShapesDemo()
            }

            SectionHeader("Paths & Curves")

            DemoCard(title = "Bezier Curves", description = "Smooth curves using Path") {
                BezierCurvesDemo()
            }

            SectionHeader("Gradients")

            DemoCard(title = "Linear Gradient", description = "Smooth color transitions") {
                LinearGradientDemo()
            }

            SectionHeader("Custom Drawing")

            DemoCard(title = "Chart Drawing", description = "Simple bar chart using Canvas") {
                BarChartDemo()
            }

            DemoCard(title = "Donut Chart", description = "Circular progress chart") {
                DonutChartDemo()
            }
        }
    }
}

@Composable
fun BasicShapesDemo() {
    Canvas(modifier = Modifier.fillMaxWidth().height(150.dp)) {
        val size = this.size
        val shapeSize = size.minDimension / 4

        // Circle
        drawCircle(
            color = Color(0xFF6650a4),
            radius = shapeSize,
            center = Offset(shapeSize, shapeSize)
        )

        // Rectangle
        drawRect(
            color = Color(0xFF625b71),
            topLeft = Offset(shapeSize * 3, 0f),
            size = Size(shapeSize * 2, shapeSize * 2)
        )

        // Rounded Rectangle
        drawRoundRect(
            color = Color(0xFF7D5260),
            topLeft = Offset(shapeSize * 6, 0f),
            size = Size(shapeSize * 2, shapeSize * 2),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(20f, 20f)
        )

        // Oval
        drawOval(
            color = Color(0xFF0061A4),
            topLeft = Offset(shapeSize, shapeSize * 3),
            size = Size(shapeSize * 2, shapeSize)
        )

        // Line
        drawLine(
            color = Color(0xFF386A20),
            start = Offset(0f, size.height - 20),
            end = Offset(size.width, size.height - 20),
            strokeWidth = 4f
        )
    }
}

@Composable
fun BezierCurvesDemo() {
    Canvas(modifier = Modifier.fillMaxWidth().height(150.dp)) {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(0f, height / 2)
            quadraticTo(
                width / 4, 0f,
                width / 2, height / 2
            )
            quadraticTo(
                width * 3 / 4, height,
                width, height / 2
            )
        }

        drawPath(
            path = path,
            color = Color(0xFF6650a4),
            style = Stroke(width = 4f)
        )

        // Fill under curve
        val fillPath = Path().apply {
            addPath(path)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }

        drawPath(
            path = fillPath,
            color = Color(0xFF6650a4).copy(alpha = 0.2f)
        )
    }
}

@Composable
fun LinearGradientDemo() {
    Canvas(modifier = Modifier.fillMaxWidth().height(100.dp)) {
        val gradient = Brush.linearGradient(
            colors = listOf(
                Color(0xFF6650a4),
                Color(0xFF625b71),
                Color(0xFF7D5260),
                Color(0xFF0061A4)
            )
        )
        drawRoundRect(
            brush = gradient,
            size = size,
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f)
        )
    }
}

@Composable
fun BarChartDemo() {
    val data = listOf(30f, 50f, 80f, 45f, 65f, 90f, 40f)
    val colors = listOf(
        Color(0xFF6650a4),
        Color(0xFF625b71),
        Color(0xFF7D5260),
        Color(0xFF0061A4),
        Color(0xFF386A20),
        Color(0xFF8A4E00),
        Color(0xFFBA1A1A)
    )

    Canvas(modifier = Modifier.fillMaxWidth().height(200.dp)) {
        val width = size.width
        val height = size.height
        val barWidth = width / (data.size * 2)
        val maxValue = data.maxOrNull() ?: 1f

        data.forEachIndexed { index, value ->
            val barHeight = (value / maxValue) * height
            val x = index * (barWidth * 2) + barWidth / 2
            val y = height - barHeight

            drawRoundRect(
                color = colors[index % colors.size],
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
            )
        }
    }
}

@Composable
fun DonutChartDemo() {
    val segments = listOf(0.3f, 0.25f, 0.2f, 0.15f, 0.1f)
    val colors = listOf(
        Color(0xFF6650a4),
        Color(0xFF625b71),
        Color(0xFF7D5260),
        Color(0xFF0061A4),
        Color(0xFF386A20)
    )

    Canvas(modifier = Modifier.size(150.dp)) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2
        val strokeWidth = radius * 0.4f

        var startAngle = -90f
        segments.forEachIndexed { index, portion ->
            val sweepAngle = portion * 360f
            drawArc(
                color = colors[index],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                size = androidx.compose.ui.geometry.Size(radius * 2 - strokeWidth, radius * 2 - strokeWidth),
                style = Stroke(width = strokeWidth)
            )
            startAngle += sweepAngle
        }
    }
}
