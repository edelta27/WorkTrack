package com.worktrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Witaj 👋")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Zacznij śledzić swój czas pracy")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onStartClick) {
            Text("ZACZYNAMY")
        }
    }
}