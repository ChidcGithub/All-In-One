package com.allinone.feature.system

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import com.allinone.MainActivity
import com.allinone.core.ui.components.DemoCard
import com.allinone.core.ui.components.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val notificationManager = remember { context.getSystemService(NotificationManager::class.java) }

    fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel group
            val group = NotificationChannelGroup("demo_group", "Demo Channels")
            notificationManager?.createNotificationChannelGroup(group)

            // Basic channel
            val basicChannel = NotificationChannel(
                "basic_channel",
                "Basic Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                group = "demo_group"
                description = "Channel for basic notifications"
            }

            // Rich media channel
            val richChannel = NotificationChannel(
                "rich_channel",
                "Rich Media Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                group = "demo_group"
                description = "Channel for rich media notifications"
            }

            // Progress channel
            val progressChannel = NotificationChannel(
                "progress_channel",
                "Progress Notifications",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                group = "demo_group"
                description = "Channel for progress notifications"
            }

            notificationManager?.createNotificationChannel(basicChannel)
            notificationManager?.createNotificationChannel(richChannel)
            notificationManager?.createNotificationChannel(progressChannel)
        }
    }

    fun showBasicNotification() {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "basic_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("All in one Demo")
            .setContentText("This is a basic notification")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager?.notify(1, notification)
    }

    fun showActionableNotification() {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val actionIntent = Intent("com.allinone.ACTION").let {
            PendingIntent.getBroadcast(
                context, 0, it,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        val notification = NotificationCompat.Builder(context, "basic_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Action Required")
            .setContentText("Tap an action below")
            .addAction(android.R.drawable.ic_menu_call, "Action 1", actionIntent)
            .addAction(android.R.drawable.ic_menu_send, "Action 2", actionIntent)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager?.notify(2, notification)
    }

    fun showProgressNotification() {
        val notification = NotificationCompat.Builder(context, "progress_channel")
            .setSmallIcon(android.R.drawable.ic_menu_upload)
            .setContentTitle("Downloading...")
            .setContentText("Progress: 50%")
            .setProgress(100, 50, false)
            .build()

        notificationManager?.notify(3, notification)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
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
            SectionHeader("Notification Channels")

            DemoCard(
                title = "Create Channel Groups",
                description = "Organize notifications into channel groups (Android 8.0+)"
            ) {
                Button(
                    onClick = { createChannels() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create Channels")
                }
            }

            SectionHeader("Notification Types")

            DemoCard(
                title = "Basic Notification",
                description = "Simple notification with title and text"
            ) {
                Button(
                    onClick = { showBasicNotification() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show Basic Notification")
                }
            }

            DemoCard(
                title = "Actionable Notification",
                description = "Notification with action buttons"
            ) {
                Button(
                    onClick = { showActionableNotification() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show Actionable Notification")
                }
            }

            DemoCard(
                title = "Progress Notification",
                description = "Notification showing download/upload progress"
            ) {
                Button(
                    onClick = { showProgressNotification() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show Progress Notification")
                }
            }

            DemoCard(
                title = "Rich Media Notification",
                description = "Notification with images and expanded content"
            ) {
                Button(
                    onClick = {
                        val bigPictureStyle = NotificationCompat.BigPictureStyle()
                            .bigPicture(android.graphics.Bitmap.createBitmap(100, 100, android.graphics.Bitmap.Config.ARGB_8888))
                            .bigLargeIcon(null)

                        val notification = NotificationCompat.Builder(context, "rich_channel")
                            .setSmallIcon(android.R.drawable.ic_menu_gallery)
                            .setContentTitle("Rich Media")
                            .setContentText("Check out this image!")
                            .setStyle(bigPictureStyle)
                            .build()

                        notificationManager?.notify(4, notification)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show Rich Media Notification")
                }
            }

            SectionHeader("Notification Info")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Channel Groups: demo_group\n" +
                                "Channels: basic_channel, rich_channel, progress_channel\n" +
                                "Notifications shown will appear in the system notification shade.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
