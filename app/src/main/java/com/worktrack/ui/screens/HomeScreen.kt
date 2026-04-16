package com.worktrack.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.worktrack.data.local.FakeDatabase
import com.worktrack.data.model.Company
import java.util.Calendar


@Composable
fun HomeScreen(
    onCompanyClick: (Long) -> Unit,
    onAddClick: () -> Unit,
    onHistoryClick: () -> Unit
) {
    var refreshTrigger by remember { mutableStateOf(0) }

    var companyToDelete by remember { mutableStateOf<Company?>(null) }

    var companyToEdit by remember { mutableStateOf<Company?>(null) }
    var editedCompanyName by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            FakeDatabase.companies.forEach { company ->

                val jobsForCompany = FakeDatabase.jobs
                    .filter { it.companyId == company.id }

                val currentMonth = getCurrentMonth()

                val totalHours = FakeDatabase.entries
                    .filter { entry ->
                        entry.date.startsWith(currentMonth) &&
                                jobsForCompany.any { it.id == entry.jobId }
                    }
                    .sumOf { it.hours }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onCompanyClick(company.id)
                            }
                    ) {
                        Text(text = company.name)

                        Text(
                            text = "${"%.1f".format(totalHours)} h",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Row {

                        Text(
                            text = "Edytuj",
                            modifier = Modifier
                                .clickable {
                                    companyToEdit = company
                                    editedCompanyName = company.name
                                }
                                .padding(8.dp)
                        )

                        Text(
                            text = "Usuń",
                            modifier = Modifier
                                .clickable {
                                    companyToDelete = company
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }

//            FakeDatabase.jobs.forEach { job ->
//
//                val total = FakeDatabase.entries
//                    .filter { it.jobId == job.id }
//                    .sumOf { it.hours }
//
//                Text("${job.name} - ${"%.2f".format(total)} h")
//            }

            Button(
                onClick = onHistoryClick,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text("Historia")
            }

            Box(modifier = Modifier.fillMaxSize()) {
                FloatingActionButton(
                    onClick = onAddClick,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Text("+")
                }
            }
        }

        companyToDelete?.let { company ->

            AlertDialog(
                onDismissRequest = { companyToDelete = null },

                title = { Text("Usuń firmę") },

                text = {
                    Text("Czy na pewno chcesz usunąć firmę wraz ze wszystkimi zleceniami i godzinami?")
                },

                confirmButton = {
                    Button(onClick = {

                        val jobsToRemove = FakeDatabase.jobs
                            .filter { it.companyId == company.id }

                        jobsToRemove.forEach { job ->
                            FakeDatabase.entries.removeAll { it.jobId == job.id }
                        }

                        FakeDatabase.jobs.removeAll { it.companyId == company.id }

                        FakeDatabase.companies.remove(company)

                        companyToDelete = null
                    }) {
                        Text("Tak")
                    }
                },

                dismissButton = {
                    Button(onClick = {
                        companyToDelete = null
                    }) {
                        Text("Nie")
                    }
                }
            )
        }

        companyToEdit?.let { company ->

            AlertDialog(
                onDismissRequest = { companyToEdit = null },

                title = { Text("Edytuj firmę") },

                text = {
                    TextField(
                        value = editedCompanyName,
                        onValueChange = { editedCompanyName = it },
                        label = { Text("Nazwa firmy") }
                    )
                },

                confirmButton = {
                    Button(onClick = {
                        if (editedCompanyName.isNotBlank()) {
                            company.name = editedCompanyName
                        }
                        companyToEdit = null
                    }) {
                        Text("Zapisz")
                    }
                },

                dismissButton = {
                    Button(onClick = {
                        companyToEdit = null
                    }) {
                        Text("Anuluj")
                    }
                }
            )
        }
    }
}

fun getCurrentMonth(): String {
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1

    return "$year-${month.toString().padStart(2, '0')}"
}
