package com.worktrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.worktrack.data.local.FakeDatabase
import com.worktrack.data.model.WorkEntry
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun TimerScreen(
    jobId: Long,
    onBack: () -> Unit
) {
    var isRunning by remember { mutableStateOf(false) }
    var seconds by remember { mutableStateOf(0) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000)
            seconds++
        }
    }

    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60

    val timeText = String.format("%02d:%02d:%02d", hours, minutes, secs)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Timer")

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = timeText,
            style = MaterialTheme.typography.displayLarge,
            color = if (isRunning) MaterialTheme.colorScheme.primary else Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { isRunning = !isRunning }
        ) {
            Text(if (isRunning) "STOP" else "START")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (seconds > 0) {

                val hoursValue = seconds / 3600.0

                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = (calendar.get(Calendar.MONTH) + 1).toString().padStart(2, '0')
                val day = calendar.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')

                val currentDate = "$year-$month-$day"

                FakeDatabase.entries.add(
                    WorkEntry(
                        id = System.currentTimeMillis(),
                        jobId = jobId,
                        hours = hoursValue,
                        date = currentDate
                    )
                )

                isRunning = false
                seconds = 0
                onBack()
            }
        }) {
            Text("Zapisz")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            isRunning = false
            seconds = 0
        }) {
            Text("Reset")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onBack) {
            Text("Wróć")
        }
    }
}