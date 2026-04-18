package com.worktrack.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EntryOptionsScreen(
    jobId: Long,
    onBack: () -> Unit,
    onManualClick: () -> Unit,
    onTimerClick: () -> Unit
) {

    Column(modifier = Modifier.padding(16.dp)) {

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onManualClick) {
            Text("Dodaj ręcznie")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onTimerClick) {
            Text("Uruchom timer")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text("Wróć")
        }
    }
}
