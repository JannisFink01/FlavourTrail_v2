package com.example.flavourtrail_v2.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.flavourtrail_v2.R

@Composable
fun NavigationBar(onItemSelected: (String) -> Unit) {
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
                    contentDescription = "Home Icon"
                )
            },
            label = {
                Text("Home", style = MaterialTheme.typography.labelSmall)
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("Routes") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_routes),
                    contentDescription = "Routes Icon"
                )
            },
            label = {
                Text("Routes", style = MaterialTheme.typography.labelSmall)
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("Premium") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_premium),
                    contentDescription = "Premium Icon"
                )
            },
            label = {
                Text("Premium", style = MaterialTheme.typography.labelSmall)
            }
        )
    }
}

