package com.worktrack.ui.screens

import androidx.compose.ui.graphics.Color
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun AddEntryScreen(
    jobId: Long,
    onBack: () -> Unit
) {
    var hoursText by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val today = "$year-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}"

    var date by remember {
        mutableStateOf(today)
    }

    var error by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Spacer(modifier = Modifier.height(20.dp))

        Text("Dodaj godziny")

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = hoursText,
            onValueChange = { hoursText = it },
            label = { Text("Godziny") }
        )
        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Data (rrrr-mm-dd)") }
        )

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {

            val hoursValue = hoursText.toDoubleOrNull()

            if (hoursValue == null) {
                error = "Niepoprawna liczba godzin"
            } else if (!isValidDate(date)) {
                error = "Niepoprawna data (rrrr-mm-dd)"
            } else {
                error = ""

                FakeDatabase.entries.add(
                    WorkEntry(
                        id = System.currentTimeMillis(),
                        jobId = jobId,
                        hours = hoursValue,
                        date = date
                    )
                )

                onBack()
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

fun isValidDate(date: String): Boolean {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.isLenient = false
        sdf.parse(date)
        true
    } catch (e: Exception) {
        false
    }
}
