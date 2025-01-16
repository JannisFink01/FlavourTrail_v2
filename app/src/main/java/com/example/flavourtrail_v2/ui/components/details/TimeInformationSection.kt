package com.example.flavourtrail_v2.ui.components.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavourtrail_v2.R

@Composable
fun TimeInformationSection(){
    Column{
        Row{
            Icon(
                painter = painterResource(id = R.drawable.clock_icon), // Use a built-in Material Icon
                contentDescription = "Clock Icon", // Provide a description for accessibility
                modifier = Modifier.size(24.dp), // Adjust size if needed
                tint = Color.Black // Optional: Set a tint color
            )
            Text(
                text = "Opening Hours",
                fontSize = 20.sp
            )
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.hourglass_icon), // Use a custom icon
                contentDescription = "Hourglass Icon", // Provide a description for accessibility
                modifier = Modifier.size(24.dp), // Adjust size if needed
            )
            Text(
                text = "Time spent",
                fontSize = 20.sp
            )
        }
    }
}