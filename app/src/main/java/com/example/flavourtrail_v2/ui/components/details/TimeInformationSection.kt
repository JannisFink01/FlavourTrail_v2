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

/**
 * A composable function that displays time-related information about a destination.
 *
 * The `TimeInformationSection` includes two rows:
 * - One for displaying opening hours with a clock icon.
 * - Another for displaying the expected time spent at the destination with an hourglass icon.
 */
@Composable
fun TimeInformationSection() {
    Column {
        // Row for displaying opening hours
        Row {
            Icon(
                painter = painterResource(id = R.drawable.clock_icon), // Clock icon for opening hours
                contentDescription = "Clock Icon", // Accessibility description
                modifier = Modifier.size(24.dp), // Icon size
                tint = Color.Black // Icon tint color
            )
            Text(
                text = "Opening Hours", // Label text
                fontSize = 20.sp // Text size
            )
        }

        // Row for displaying time spent
        Row {
            Icon(
                painter = painterResource(id = R.drawable.hourglass_icon), // Hourglass icon for time spent
                contentDescription = "Hourglass Icon", // Accessibility description
                modifier = Modifier.size(24.dp) // Icon size
            )
            Text(
                text = "Time spent", // Label text
                fontSize = 20.sp // Text size
            )
        }
    }
}
