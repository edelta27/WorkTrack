package com.worktrack.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun EditEntriesScreen(onBack: () -> Unit) {
    Column {
        Text("Edytuj godziny")

        Button(onClick = onBack) {
            Text("Wróć")
        }
    }
}
