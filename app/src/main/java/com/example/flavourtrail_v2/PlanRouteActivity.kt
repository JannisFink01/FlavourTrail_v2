package com.example.flavourtrail_v2

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class PlanRouteActivity : BaseActivity() {
    @Composable
    override fun Content() {
        PlanRouteScreen()
    }
}

@Composable
fun PlanRouteScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Choose your city",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { /* Action */ }, modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(text = "My Location")
                }

                val cityName = remember { mutableStateOf("") }
                OutlinedTextField(
                    value = cityName.value,
                    onValueChange = { cityName.value = it },
                    label = { Text("Enter city") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}