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


@Composable
fun HomeScreen(
    onCompanyClick: (Long) -> Unit,
    onAddClick: () -> Unit
) {
    var refreshTrigger by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
        LaunchedEffect(refreshTrigger) {

        }
            Spacer(modifier = Modifier.height(20.dp))

            FakeDatabase.companies.forEach { company ->

                val jobsForCompany = FakeDatabase.jobs
                    .filter { it.companyId == company.id }

                val totalHours = FakeDatabase.entries
                    .filter { entry -> jobsForCompany.any { it.id == entry.jobId } }
                    .sumOf { it.hours }

                Text(
                    text = "${company.name} - ${"%.1f".format(totalHours)} h",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                        onCompanyClick(company.id)
                    }
                        .padding(12.dp)
                )
            }

            FakeDatabase.jobs.forEach { job ->

                val total = FakeDatabase.entries
                    .filter { it.jobId == job.id }
                    .sumOf { it.hours }

                Text("${job.name} - ${"%.2f".format(total)} h")
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
    }
}
