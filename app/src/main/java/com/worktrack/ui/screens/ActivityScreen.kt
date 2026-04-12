package com.worktrack.ui.screens

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
import com.worktrack.data.model.Company

@Composable
fun ActivityScreen(onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Spacer(modifier = Modifier.height(20.dp))

        Text("Dodaj firmę")

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nazwa firmy") }
        )

        Button(onClick = {
            if (name.isNotBlank()) {
                FakeDatabase.companies.add(
                    Company(
                        id = System.currentTimeMillis(),
                        name = name
                    )
                )
                name = ""
            }
        }) {
            Text("Dodaj")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text("Wróć")
        }
    }

}


