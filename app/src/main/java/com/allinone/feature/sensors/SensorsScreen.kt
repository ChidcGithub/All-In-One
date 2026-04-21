package com.allinone.feature.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.allinone.core.ui.components.DemoCard
import com.allinone.core.ui.components.SectionHeader
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(android.content.Context.SENSOR_SERVICE) as SensorManager }

    var accelerometerData by remember { mutableStateOf("X: 0.0, Y: 0.0, Z: 0.0") }
    var gyroscopeData by remember { mutableStateOf("X: 0.0, Y: 0.0, Z: 0.0") }
    var magnetometerData by remember { mutableStateOf("X: 0.0, Y: 0.0, Z: 0.0") }
    var lightLevel by remember { mutableStateOf(0f) }
    var isProximate by remember { mutableStateOf(false) }
    var stepCount by remember { mutableStateOf(0) }

    val availableSensors = remember { mutableStateListOf<Sensor>() }

    DisposableEffect(Unit) {
        // Get available sensors
        val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)
        availableSensors.clear()
        availableSensors.addAll(sensorList)

        // Accelerometer
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val accelerometerListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                accelerometerData = "X: ${event.values[0].toString().take(5)}, Y: ${event.values[1].toString().take(5)}, Z: ${event.values[2].toString().take(5)}"
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        // Gyroscope
        val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        val gyroscopeListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                gyroscopeData = "X: ${event.values[0].toString().take(5)}, Y: ${event.values[1].toString().take(5)}, Z: ${event.values[2].toString().take(5)}"
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        // Magnetometer
        val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        val magnetometerListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                magnetometerData = "X: ${event.values[0].toString().take(5)}, Y: ${event.values[1].toString().take(5)}, Z: ${event.values[2].toString().take(5)}"
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        // Light sensor
        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        val lightListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                lightLevel = event.values[0]
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        // Proximity sensor
        val proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        val proximityListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                isProximate = event.values[0] < 5f
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        // Step counter
        val stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        val stepListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                stepCount = event.values[0].toInt()
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        // Register listeners
        accelerometer?.let { sensorManager.registerListener(accelerometerListener, it, SensorManager.SENSOR_DELAY_UI) }
        gyroscope?.let { sensorManager.registerListener(gyroscopeListener, it, SensorManager.SENSOR_DELAY_UI) }
        magnetometer?.let { sensorManager.registerListener(magnetometerListener, it, SensorManager.SENSOR_DELAY_UI) }
        lightSensor?.let { sensorManager.registerListener(lightListener, it, SensorManager.SENSOR_DELAY_UI) }
        proximitySensor?.let { sensorManager.registerListener(proximityListener, it, SensorManager.SENSOR_DELAY_UI) }
        stepCounter?.let { sensorManager.registerListener(stepListener, it, SensorManager.SENSOR_DELAY_UI) }

        onDispose {
            sensorManager.unregisterListener(accelerometerListener)
            sensorManager.unregisterListener(gyroscopeListener)
            sensorManager.unregisterListener(magnetometerListener)
            sensorManager.unregisterListener(lightListener)
            sensorManager.unregisterListener(proximityListener)
            sensorManager.unregisterListener(stepListener)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Sensors") },
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
            SectionHeader("Motion Sensors")

            DemoCard(
                title = "Accelerometer",
                description = "Measures acceleration force on all three axes"
            ) {
                Text(
                    text = accelerometerData,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }

            DemoCard(
                title = "Gyroscope",
                description = "Measures rotation rate around three axes"
            ) {
                Text(
                    text = gyroscopeData,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }

            DemoCard(
                title = "Magnetometer",
                description = "Measures magnetic field strength"
            ) {
                Text(
                    text = magnetometerData,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }

            SectionHeader("Environmental Sensors")

            DemoCard(
                title = "Light Sensor",
                description = "Measures ambient light level in lux"
            ) {
                Column {
                    LinearProgressIndicator(
                        progress = { (lightLevel / 1000f).coerceIn(0f, 1f) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "${lightLevel.toInt()} lux",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            DemoCard(
                title = "Proximity Sensor",
                description = "Detects nearby objects"
            ) {
                Text(
                    text = if (isProximate) "Object detected nearby!" else "No nearby objects",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            SectionHeader("Activity Sensors")

            DemoCard(
                title = "Step Counter",
                description = "Tracks total steps taken"
            ) {
                Text(
                    text = "$stepCount steps",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            SectionHeader("Available Sensors")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Found ${availableSensors.size} sensors:",
                        style = MaterialTheme.typography.labelLarge
                    )
                    availableSensors.take(15).forEach { sensor ->
                        Text(
                            text = "- ${sensor.name} (${sensor.vendor})",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                    if (availableSensors.size > 15) {
                        Text(
                            text = "...and ${availableSensors.size - 15} more",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
