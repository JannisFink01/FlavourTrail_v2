package com.example.flavourtrail_v2.ui.components.details

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A composable function for displaying a customizable "Book Now" button.
 *
 * This button spans the full width of its container, has rounded corners, and displays a specified
 * text label. The button can be styled and used for navigation or triggering actions.
 *
 * @param buttonText The text to display on the button.
 */
@Composable
fun BookNowButton(buttonText: String) {
    Button(
        onClick = { /* Handle button click */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            // Uncomment and customize `containerColor` if needed for a specific color.
            // containerColor = Color.Blue,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = buttonText,
            fontSize = 20.sp
        )
    }
}
