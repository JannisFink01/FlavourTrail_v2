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


@Composable
fun RateDestinationButton(placeReviewViewModel: PlaceReviewViewModel, placeId: Int) {
    var showPopup by remember { mutableStateOf(false) }
    Button(
        onClick = { showPopup = true },
        modifier = Modifier.width(200.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(10.dp)
    )
    {
        Icon(
            imageVector = Icons.Filled.Star, // Use a built-in Material Icon
            contentDescription = "Star Icon", // Provide a description for accessibility
            modifier = Modifier.size(24.dp), // Adjust size if needed
            tint = Color.DarkGray // Optional: Set a tint color
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Rate Destination",
            fontSize = 20.sp
        )
        if (showPopup) {
            RatingPopup(
                placeReviewViewModel = placeReviewViewModel,
                placeId = placeId,
                onDismiss = { showPopup = false })
        }
    }
}
