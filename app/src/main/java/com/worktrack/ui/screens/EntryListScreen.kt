package com.worktrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.worktrack.data.local.FakeDatabase
import com.worktrack.data.model.WorkEntry
import com.worktrack.ui.components.AppCard

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

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(entries){ entry ->

                AppCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "${entry.date}",
                                style = MaterialTheme.typography.titleMedium)

                            Text(text = "${"%.2f".format(entry.hours)} h",
                                style = MaterialTheme.typography.bodySmall)
                        }

                        Row {

                            IconButton(onClick = {
                                onEditClick(entry.id)
                            }) {
                                Text("✏️")
                            }

                            IconButton(onClick = {
                                entryToDelete = entry
                            }) {
                                Text("🗑️")
                            }

                        }
                    }
                }
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