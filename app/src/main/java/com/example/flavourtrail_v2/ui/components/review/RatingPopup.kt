package com.example.flavourtrail_v2.ui.components.review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavourtrail_v2.PlaceReviewViewModel
import com.example.flavourtrail_v2.data.entity.PlaceReview
import com.example.flavourtrail_v2.ui.components.review.StarRatingBar
import java.util.Date

@Composable
fun RatingPopup(viewModel: PlaceReviewViewModel) {
    var text by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0f)}
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Bewertung",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            StarRatingBar(
                maxStars = 5,
                initialRating = 0f,
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = text,
                onValueChange = { newText : String -> text = newText },
                label = { Text("Kommentar") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = {
                val placeReview = PlaceReview(
                    placeId = 1, // TODO Beispielwert
                    userId = 1, // TODO Beispielwert
                    rating = rating.toInt(),
                    comment = text,
                    date = Date() // aktuelles Datum
                )
               viewModel.insertPlaceReview(placeReview)
            }) {
                Text("Speichern")
            }
        }
    }
}

@Preview
@Composable
fun PreviewRatingPopup() {

}

