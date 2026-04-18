package com.worktrack.ui.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
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
import com.worktrack.data.model.Job

@Composable
fun JobListScreen(
    companyId: Long,
    onBack: () -> Unit,
    onJobClick: (Long) -> Unit,
    onAddEntryClick: (Long) -> Unit
) {
    var jobName by remember { mutableStateOf("") }

    val jobs = FakeDatabase.jobs.filter { it.companyId == companyId }

    var jobToDelete by remember { mutableStateOf<Job?>(null) }

    var jobToEdit by remember { mutableStateOf<Job?>(null) }
    var editedName by remember { mutableStateOf("") }


    Column(modifier = Modifier.padding(16.dp)) {

        Spacer(modifier = Modifier.height(20.dp))

        Text("Zlecenia")

        TextField(
            value = jobName,
            onValueChange = { jobName = it },
            label = { Text("Nazwa zlecenia") }
        )

        Button(onClick = {
            if (jobName.isNotBlank()) {
                FakeDatabase.jobs.add(
                    Job(
                        id = System.currentTimeMillis(),
                        companyId = companyId,
                        name = jobName
                    )
                )
                jobName = ""
            }
        }) {
            Text("Dodaj zlecenie")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Lista zleceń:")

        LazyColumn {
            items(jobs) { job ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {

                        Text(
                            text = job.name,
                            modifier = Modifier.clickable {
                                    onJobClick(job.id)
                                }
                        )

                        val currentMonth = getCurrentMonth()

                        val totalHours = FakeDatabase.entries
                            .filter {
                                it.jobId == job.id &&
                                        it.date.startsWith(currentMonth)
                            }
                            .sumOf { it.hours }

                        Text(
                            text = "${"%.1f".format(totalHours)}h",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Row {
                        Text(
                            text = "Edytuj",
                            modifier = Modifier
                                .clickable {
                                    jobToEdit = job
                                    editedName = job.name
                                }
                                .padding(8.dp)
                        )

                        Text(
                            text = "Usuń",
                            modifier = Modifier.clickable {
                                jobToDelete = job
                            }
                        )

                        Text(
                            text = "+",
                            modifier = Modifier
                                .clickable {
                                    onAddEntryClick(job.id)
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }
        }

        jobToDelete?.let { job ->

            AlertDialog(
                onDismissRequest = { jobToDelete = null },
                title = { Text("Usuń zlecenie") },
                text = { Text("Czy na pewno chcesz usunąć?") },

                confirmButton = {
                    Button(onClick = {
                        FakeDatabase.jobs.remove(job)
                        jobToDelete = null
                    }) {
                        Text("Tak")
                    }
                },

                dismissButton = {
                    Button(onClick = {
                        jobToDelete = null
                    }) {
                        Text("Nie")
                    }
                }
            )
        }

        jobToEdit?.let { job ->

            AlertDialog(
                onDismissRequest = { jobToEdit = null },
                title = { Text("Edytuj zlecenie") },

                text = {
                    TextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        label = { Text("Nazwa") }
                    )
                },

                confirmButton = {
                    Button(onClick = {
                        if (editedName.isNotBlank()) {
                            job.name = editedName
                        }
                        jobToEdit = null
                    }) {
                        Text("Zapisz")
                    }
                },

                dismissButton = {
                    Button(onClick = {
                        jobToEdit = null
                    }) {
                        Text("Anuluj")
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

