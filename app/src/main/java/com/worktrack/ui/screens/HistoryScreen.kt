package com.worktrack.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.worktrack.data.local.FakeDatabase
import com.worktrack.ui.components.AppCard

@Composable
fun HistoryScreen(onBack: () -> Unit) {

    val entries = FakeDatabase.entries

    val grouped = entries.groupBy { entry ->
        entry.date.substring(0, 7)
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Spacer(modifier = Modifier.height(16.dp))

        if (grouped.isEmpty()) {
            Text("Brak danych")
        } else {

            LazyColumn {

                grouped.forEach { (month, entriesForMonth) ->

                    val totalMonth = entriesForMonth.sumOf { it.hours }

                    val entriesByCompany = entriesForMonth.groupBy { entry ->
                        val job = FakeDatabase.jobs.find { it.id == entry.jobId }
                        job?.companyId
                    }

                    item {
                        AppCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {

                                Text(
                                    text = "$month - ${"%.1f".format(totalMonth)} h",
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                entriesByCompany.forEach { (companyId, companyEntries) ->

                                    val company = FakeDatabase.companies.find { it.id == companyId }
                                    val totalCompany = companyEntries.sumOf { it.hours }

                                    Text(
                                        text = "${company?.name ?: "Brak"} - ${
                                            "%.1f".format(
                                                totalCompany
                                            )
                                        } h",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(vertical = 2.dp)
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text("Wróć")
        }
    }
}