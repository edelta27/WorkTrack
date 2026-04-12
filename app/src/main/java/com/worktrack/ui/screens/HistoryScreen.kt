package com.worktrack.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HistoryScreen(onBack: () -> Unit) {
    Column {
        Text("Historia")

        Button(onClick = onBack) {
            Text("Wróć")
        }
    }

}
