package com.allinone.feature.background

import android.app.AlarmManager
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.allinone.core.ui.components.DemoCard
import com.allinone.core.ui.components.SectionHeader
import com.allinone.feature.background.worker.ChainedDemoWorker
import com.allinone.feature.background.worker.DemoWorker
import com.allinone.feature.background.worker.PeriodicDemoWorker
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackgroundTasksScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var workManagerStatus by remember { mutableStateOf("No work scheduled") }
    var alarmStatus by remember { mutableStateOf("No alarm set") }
    var jobSchedulerStatus by remember { mutableStateOf("No job scheduled") }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Background Tasks") },
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
            SectionHeader("WorkManager")

            DemoCard(
                title = "One-Time Work",
                description = "Schedule a one-time background task"
            ) {
                Column {
                    Button(
                        onClick = {
                            val workRequest = OneTimeWorkRequestBuilder<DemoWorker>()
                                .setInputData(
                                    workDataOf(DemoWorker.KEY_TASK_NAME to "Demo Task")
                                )
                                .build()

                            WorkManager.getInstance(context).enqueue(workRequest)
                            workManagerStatus = "One-time work scheduled"

                            // Observe work status
                            WorkManager.getInstance(context).getWorkInfoByIdLiveData(workRequest.id)
                                .observeForever { workInfo ->
                                    if (workInfo != null) {
                                        when (workInfo.state) {
                                            WorkInfo.State.RUNNING -> {
                                                workManagerStatus = "Work running..."
                                            }
                                            WorkInfo.State.SUCCEEDED -> {
                                                val result = workInfo.outputData.getString("result") ?: "Done"
                                                workManagerStatus = "Completed: $result"
                                            }
                                            WorkInfo.State.FAILED -> {
                                                workManagerStatus = "Work failed"
                                            }
                                            else -> {}
                                        }
                                    }
                                }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Schedule One-Time Work")
                    }
                    Text(
                        text = workManagerStatus,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            DemoCard(
                title = "Periodic Work",
                description = "Schedule recurring background tasks"
            ) {
                Column {
                    Button(
                        onClick = {
                            val workRequest = PeriodicWorkRequestBuilder<PeriodicDemoWorker>(
                                15, TimeUnit.MINUTES
                            )
                                .setConstraints(
                                    Constraints.Builder()
                                        .setRequiredNetworkType(NetworkType.CONNECTED)
                                        .build()
                                )
                                .build()

                            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                                "periodic_demo",
                                androidx.work.ExistingPeriodicWorkPolicy.KEEP,
                                workRequest
                            )
                            workManagerStatus = "Periodic work scheduled (runs every 15 min with network)"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Schedule Periodic Work")
                    }
                    Text(
                        text = workManagerStatus,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            DemoCard(
                title = "Chained Work",
                description = "Chain multiple work requests together"
            ) {
                Column {
                    Button(
                        onClick = {
                            val step1 = OneTimeWorkRequestBuilder<DemoWorker>()
                                .setInputData(
                                    workDataOf(DemoWorker.KEY_TASK_NAME to "Chain Step 1")
                                )
                                .build()

                            val step2 = OneTimeWorkRequestBuilder<ChainedDemoWorker>()
                                .setInputData(
                                    workDataOf("previous_result" to "From Step 1")
                                )
                                .build()

                            WorkManager.getInstance(context)
                                .beginWith(step1)
                                .then(step2)
                                .enqueue()

                            workManagerStatus = "Work chain enqueued: Step1 -> Step2"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Schedule Work Chain")
                    }
                    Text(
                        text = workManagerStatus,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            SectionHeader("AlarmManager")

            DemoCard(
                title = "Exact Alarm",
                description = "Schedule precise alarm for exact timing"
            ) {
                Column {
                    Button(
                        onClick = {
                            val alarmManager = context.getSystemService(AlarmManager::class.java)
                            val intent = Intent(context, com.allinone.feature.system.receiver.DemoBroadcastReceiver::class.java)
                            val pendingIntent = PendingIntent.getBroadcast(
                                context, 0, intent,
                                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                            )
                            val triggerTime = System.currentTimeMillis() + 60000 // 1 minute from now

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                alarmManager.setExactAndAllowWhileIdle(
                                    AlarmManager.RTC_WAKEUP,
                                    triggerTime,
                                    pendingIntent
                                )
                            }
                            alarmStatus = "Exact alarm set for 1 minute from now"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Set Exact Alarm")
                    }
                    Text(
                        text = alarmStatus,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            DemoCard(
                title = "Repeating Alarm",
                description = "Schedule recurring alarm"
            ) {
                Button(
                    onClick = {
                        val alarmManager = context.getSystemService(AlarmManager::class.java)
                        val intent = Intent(context, com.allinone.feature.system.receiver.DemoBroadcastReceiver::class.java)
                        val pendingIntent = PendingIntent.getBroadcast(
                            context, 1, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                        val interval = AlarmManager.INTERVAL_DAY

                        alarmManager.setRepeating(
                            AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis() + interval,
                            interval,
                            pendingIntent
                        )
                        alarmStatus = "Daily repeating alarm set"
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Set Repeating Alarm")
                }
            }

            SectionHeader("JobScheduler")

            DemoCard(
                title = "JobScheduler",
                description = "Schedule background jobs with constraints"
            ) {
                Column {
                    Button(
                        onClick = {
                            // JobScheduler demo (requires JobService implementation)
                            jobSchedulerStatus = "JobScheduler requires a JobService implementation"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Schedule Job")
                    }
                    Text(
                        text = jobSchedulerStatus,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            DemoCard(
                title = "Job Constraints",
                description = "Network, charging, idle constraints"
            ) {
                Text(
                    text = "Jobs can be constrained to run only when:\n" +
                            "- Device is charging\n" +
                            "- Network is available\n" +
                            "- Device is idle\n" +
                            "- Storage is not low",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            SectionHeader("Status")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "WorkManager: $workManagerStatus\nAlarm: $alarmStatus\nJobScheduler: $jobSchedulerStatus",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
            }
        }
    }
}
