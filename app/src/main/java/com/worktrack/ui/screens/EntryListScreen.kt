package com.worktrack.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
fun EntryListScreen(
    jobId: Long,
    onBack: () -> Unit,
    onEditClick: (Long) -> Unit
) {
    var entryToDelete by remember { mutableStateOf<WorkEntry?>(null) }
    val entries = FakeDatabase.entries
        .filter { it.jobId == jobId }

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Godziny")

        Spacer(modifier = Modifier.height(8.dp))

        entries.forEach { entry ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text("${entry.date} - ${"%.2f".format(entry.hours)} h")

                Text(
                    text = "Edycja",
                    modifier = Modifier
                        .clickable {
                            onEditClick(entry.id)
                        }
                        .padding(8.dp)
                )

                Text(
                    text = "Usuń",
                    modifier = Modifier
                        .clickable {
                            entryToDelete = entry
                        }
                        .padding(8.dp)
                )
            }
        }

        entryToDelete?.let { entry ->

            AlertDialog(
                onDismissRequest = { entryToDelete = null },
                title = { Text("Usuń wpis") },
                text = { Text("Czy na pewno chcesz usunąć ten wpis?") },

                confirmButton = {
                    Button(onClick = {
                        FakeDatabase.entries.remove(entry)
                        entryToDelete = null
                    }) {
                        Text("Tak")
                    }
                },

                dismissButton = {
                    Button(onClick = {
                        entryToDelete = null
                    }) {
                        Text("Nie")
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text("Wróć")
        }
    }
}