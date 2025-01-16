package com.example.flavourtrail_v2.ui

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.flavourtrail_v2.MainActivity
import com.example.flavourtrail_v2.PlanRouteActivity
import com.example.flavourtrail_v2.R

@Composable
fun CustomNavigationBar(onItemSelected: (String) -> Unit) {
    val context = LocalContext.current // Get the current context

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        NavigationBarItem(
            selected = false,
            onClick = {
                // Navigate to MainActivity
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            },
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
            onClick = { // Navigate to PlanRouteActivity
                val intent = Intent(context, PlanRouteActivity::class.java)
                context.startActivity(intent) },
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
