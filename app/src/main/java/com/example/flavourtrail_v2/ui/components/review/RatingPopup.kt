package com.example.flavourtrail_v2.ui.components.review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.flavourtrail_v2.data.entity.PlaceReview
import java.util.Date

/**
 * Composable function that displays a popup dialog for submitting a rating and comment for a place.
 *
 * The dialog includes:
 * - A star rating bar for selecting a rating.
 * - A text field for entering a comment.
 * - A confirm button to save the review.
 * - A cancel button to dismiss the dialog without saving.
 *
 * @param placeReviewViewModel The [PlaceReviewViewModel] instance used to save the review.
 * @param placeId The ID of the place being reviewed.
 * @param onDismiss Callback invoked when the dialog is dismissed.
 */
@Composable
fun RatingPopup(placeReviewViewModel: PlaceReviewViewModel, placeId: Int, onDismiss: () -> Unit) {
    var text by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0f) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Bewertung",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp
            )
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text("Cancel")
            }
        },
        text = {
            Column {
                // Star rating bar for selecting a rating
                StarRatingBar(
                    maxStars = 5,
                    initialRating = 0f,
                    onRatingChanged = { newRating -> rating = newRating }
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Text field for entering a comment
                TextField(
                    value = text,
                    onValueChange = { newText: String -> text = newText },
                    label = { Text("Comment") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                // Create and insert the new review
                val placeReview = PlaceReview(
                    placeId = placeId, // ID of the place being reviewed
                    userId = 1, // Example user ID (replace with real data)
                    rating = rating.toInt(),
                    comment = text,
                    date = Date() // Current date
                )
                placeReviewViewModel.insertPlaceReview(placeReview)
                onDismiss() // Dismiss the dialog
            }) {
                Text("Save")
            }
        },
    )
}
