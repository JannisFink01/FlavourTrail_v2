package com.example.flavourtrail_v2.ui.components.review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavourtrail_v2.PlaceReviewViewModel
import com.example.flavourtrail_v2.data.entity.PlaceReview
import com.example.flavourtrail_v2.ui.components.review.StarRatingBar
import java.util.Date
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import com.example.flavourtrail_v2.data.ViewModel.PlaceViewModel

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
                StarRatingBar(
                    maxStars = 5,
                    initialRating = 0f,
                    onRatingChanged = { newRating -> rating = newRating }
                )
                Spacer(modifier = Modifier.height(16.dp))
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
                val placeReview = PlaceReview(
                    placeId = placeId, // TODO Beispielwert
                    userId = 1, // TODO Beispielwert
                    rating = rating.toInt(),
                    comment = text,
                    date = Date() // aktuelles Datum
                )
                placeReviewViewModel.insertPlaceReview(placeReview)
                onDismiss()
            }) {
                Text("save")
            }
        },
    )
}

@Composable
fun ShowRatingPopupButton(placeReviewViewModel: PlaceReviewViewModel, placeId: Int) {
    var showPopup by remember { mutableStateOf(false) }

    Button(onClick = { showPopup = true }) {
        Text("Open Rating Popup")
    }

    if (showPopup) {
        RatingPopup(
            placeReviewViewModel = placeReviewViewModel,
            placeId = placeId,
            onDismiss = { showPopup = false })
    }
}
