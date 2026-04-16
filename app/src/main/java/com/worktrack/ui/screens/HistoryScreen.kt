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
import com.worktrack.data.local.FakeDatabase

@Composable
fun HistoryScreen(onBack: () -> Unit) {

    val entries = FakeDatabase.entries

    val grouped = entries.groupBy { entry ->
        entry.date.substring(0, 7)
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Spacer(modifier = Modifier.height(20.dp))

        Text("Historia")

        Spacer(modifier = Modifier.height(16.dp))

        if (grouped.isEmpty()) {
            Text("Brak danych")
        }

        grouped.forEach { (month, entriesForMonth) ->

            val totalMonth = entriesForMonth.sumOf { it.hours }

            Column(modifier = Modifier.padding(8.dp)) {

                Text("$month - ${"%.1f".format(totalMonth)} h")

                val entriesByCompany = entriesForMonth.groupBy { entry ->
                    val job = FakeDatabase.jobs.find { it.id == entry.jobId }
                    job?.companyId
                }

                entriesByCompany.forEach { (companyId, companyEntries) ->

                    val company = FakeDatabase.companies.find { it.id == companyId }

                    val totalCompany = companyEntries.sumOf { it.hours }

                    Text("   ${company?.name ?: "Brak"} - ${"%.1f".format(totalCompany)} h")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = onBack) {
            Text("Wróć")
        }
    }
}
