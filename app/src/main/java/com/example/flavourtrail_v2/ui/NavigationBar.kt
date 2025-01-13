package com.example.flavourtrail_v2.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import com.example.flavourtrail_v2.R

@Composable
fun CustomNavigationBar(onItemSelected: (String) -> Unit) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("Home") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Home Icon",
                    tint = Color.White // Ensure the icon is white
                )
            },
            label = {
                Text(
                    text = "Home",
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.White) // Make text white
                )
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("Routes") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_routes),
                    contentDescription = "Routes Icon",
                    tint = Color.White // Ensure the icon is white
                )
            },
            label = {
                Text(
                    text = "Routes",
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.White) // Make text white
                )
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("Premium") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_premium),
                    contentDescription = "Premium Icon",
                    tint = Color.White // Ensure the icon is white
                )
            },
            label = {
                Text(
                    text = "Premium",
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.White) // Make text white
                )
            }
        )
    }
}
