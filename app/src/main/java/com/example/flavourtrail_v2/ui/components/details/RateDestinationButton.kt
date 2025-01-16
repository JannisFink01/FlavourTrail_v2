package com.example.flavourtrail_v2.ui.components.details

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavourtrail_v2.PlaceReviewViewModel
import com.example.flavourtrail_v2.ui.components.review.RatingPopup

/**
 * A composable function that displays a button for rating a destination.
 *
 * The button allows users to open a rating popup where they can provide feedback or reviews for a specific destination.
 * It is styled with an icon, a label, and a popup triggered upon clicking the button.
 *
 * @param placeReviewViewModel The ViewModel handling the review logic and data.
 * @param placeId The unique identifier for the place being rated.
 */
@Composable
fun RateDestinationButton(placeReviewViewModel: PlaceReviewViewModel, placeId: Int) {
    // State to manage the visibility of the rating popup
    var showPopup by remember { mutableStateOf(false) }

    // Button for triggering the rating popup
    Button(
        onClick = { showPopup = true }, // Show the popup when clicked
        modifier = Modifier.width(200.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray, // Background color of the button
            contentColor = Color.Black // Text and icon color
        ),
        shape = RoundedCornerShape(10.dp) // Rounded corners for the button
    ) {
        // Star icon to indicate rating functionality
        Icon(
            imageVector = Icons.Filled.Star, // Star icon
            contentDescription = "Star Icon", // Accessibility description
            modifier = Modifier.size(24.dp), // Icon size
            tint = Color.DarkGray // Tint color for the icon
        )
        Spacer(modifier = Modifier.width(8.dp))

        // Button label
        Text(
            text = "Rate Destination", // Text displayed on the button
            fontSize = 20.sp // Font size for the text
        )

        // Display the rating popup if the state is true
        if (showPopup) {
            RatingPopup(
                placeReviewViewModel = placeReviewViewModel, // Pass the ViewModel
                placeId = placeId, // Pass the place ID
                onDismiss = { showPopup = false } // Close the popup when dismissed
            )
        }
    }
}
