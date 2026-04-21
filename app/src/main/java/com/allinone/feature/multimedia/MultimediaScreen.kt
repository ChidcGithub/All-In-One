package com.allinone.feature.multimedia

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
fun MultimediaScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var cameraStatus by remember { mutableStateOf("Camera not initialized") }
    var audioStatus by remember { mutableStateOf("Audio not initialized") }
    var mediaStatus by remember { mutableStateOf("Media not playing") }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Multimedia") },
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
            SectionHeader("Camera (CameraX)")

            DemoCard(
                title = "Camera Preview",
                description = "Camera2 / CameraX integration"
            ) {
                Column {
                    Button(
                        onClick = { cameraStatus = "Camera preview started" },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Start Camera Preview")
                    }
                    Button(
                        onClick = { cameraStatus = "Photo captured" },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Take Photo")
                    }
                    Text(
                        text = cameraStatus,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            DemoCard(
                title = "Camera Extensions",
                description = "HDR, Night, Portrait, Face Retouch, Bokeh"
            ) {
                Button(
                    onClick = { cameraStatus = "HDR extension enabled" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Enable HDR")
                }
            }

            SectionHeader("Audio")

            DemoCard(
                title = "Microphone Recording",
                description = "Record audio using MediaRecorder"
            ) {
                Column {
                    Button(
                        onClick = { audioStatus = "Recording started" },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Start Recording")
                    }
                    Button(
                        onClick = { audioStatus = "Recording stopped" },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Stop Recording")
                    }
                    Text(
                        text = audioStatus,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            DemoCard(
                title = "Audio Playback",
                description = "Play audio using MediaPlayer"
            ) {
                Column {
                    Button(
                        onClick = { mediaStatus = "Audio playing..." },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Play Audio")
                    }
                    Button(
                        onClick = { mediaStatus = "Audio paused" },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Pause")
                    }
                    Text(
                        text = mediaStatus,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            SectionHeader("Video")

            DemoCard(
                title = "Video Playback",
                description = "Play video using ExoPlayer/Media3"
            ) {
                Column {
                    Button(
                        onClick = { mediaStatus = "Video playing..." },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Play Video")
                    }
                    Button(
                        onClick = { mediaStatus = "Video stopped" },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Stop Video")
                    }
                    Text(
                        text = mediaStatus,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            DemoCard(
                title = "Video Recording",
                description = "Record video using CameraX Video Capture"
            ) {
                Button(
                    onClick = { cameraStatus = "Video recording started" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start Video Recording")
                }
            }

            SectionHeader("MediaSession")

            DemoCard(
                title = "MediaSession Integration",
                description = "System media controls integration"
            ) {
                Text(
                    text = "MediaSession allows system-level media control from lock screen, notification shade, and wearables.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            SectionHeader("Status")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Camera: $cameraStatus\nAudio: $audioStatus\nMedia: $mediaStatus",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
            }
        }
    }
}
