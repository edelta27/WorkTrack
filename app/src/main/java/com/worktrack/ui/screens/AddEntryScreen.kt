package com.worktrack.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.worktrack.data.local.FakeDatabase
import com.worktrack.data.model.WorkEntry


@Composable
fun AddEntryScreen(
    jobId: Long,
    onBack: () -> Unit
) {
    var hoursText by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Spacer(modifier = Modifier.height(20.dp))

        Text("Dodaj godziny")

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = hoursText,
            onValueChange = { hoursText = it },
            label = { Text("Godziny") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val hours = hoursText.toDoubleOrNull()

            if (hours != null) {
                FakeDatabase.entries.add(
                    WorkEntry(
                        id = System.currentTimeMillis(),
                        jobId = jobId,
                        hours = hours,
                        date = "2026-04-12"
                    )
                )
                hoursText = ""
            }
        }) {
            Text("Zapisz")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onBack) {
            Text("Wróć")
        }
    }
}
