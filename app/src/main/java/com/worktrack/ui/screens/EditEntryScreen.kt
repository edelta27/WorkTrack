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

@Composable
fun EditEntryScreen(
    entryId: Long,
    onBack: () -> Unit
){

    val entry = FakeDatabase.entries.find { it.id == entryId }

    var hours by remember {
        mutableStateOf(entry?.hours?.toString() ?: "")
    }
    var date by remember { mutableStateOf(entry?.date ?: "") }

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Edytuj wpis")

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = hours,
            onValueChange = { hours = it },
            label = { Text("Godziny") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Data") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val updated = hours.toDoubleOrNull()

            if (updated != null && entry != null) {
                entry.hours = updated
                entry.date = date
            }

            onBack()
        }) {
            Text("Zapisz")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onBack) {
            Text("Wróć")
        }
    }
}