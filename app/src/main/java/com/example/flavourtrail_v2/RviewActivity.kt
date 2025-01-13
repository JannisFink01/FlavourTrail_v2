package com.example.flavourtrail_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flavourtrail_v2.data.AppDatabase
import com.example.flavourtrail_v2.data.entity.Place
import com.example.flavourtrail_v2.data.entity.PlaceReview
import com.example.flavourtrail_v2.data.entity.PlaceReviewWithDetails
import com.example.flavourtrail_v2.data.entity.User
import com.example.flavourtrail_v2.data.repository.PlaceReviewRepository
import com.example.flavourtrail_v2.ui.components.review.ReviewCard
import com.example.flavourtrail_v2.ui.theme.FlavourTrail_v2Theme
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class RviewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val placeId = 1 // Replace with the actual placeId you want to search for
        lifecycleScope.launch {
            val reviews = getReviewsByPlaceId(placeId)
            setContent {
                FlavourTrail_v2Theme {
                    ReviewScreen(reviews = reviews, placeId = placeId)
                }
            }
        }
    }

    private suspend fun getReviewsByPlaceId(placeId: Int): List<PlaceReviewWithDetails> {
        val repository = PlaceReviewRepository(AppDatabase.getInstance(this).placeReviewDao())
        return repository.getPlaceReviewsWithDetailsByPlaceId(placeId)
    }
}

@Composable
fun ReviewScreen(
    reviews: List<PlaceReviewWithDetails>,
    placeId: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Reviews for Place ID: $placeId", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        reviews.forEach { review ->
            ReviewCard(review = review)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val mockDate = dateFormat.parse("10.09.2024")
    val exampleReviews = listOf(
        PlaceReviewWithDetails(
            PlaceReview(
                1,
                1,
                1,
                5,
                "Great place!",
                mockDate
            ),
            User(
                1,
                "Thorsten Schmitz",
                "thomas.r.marshall@example.com",
                "password",
                false,
                image = "anomalieArt.jpg"
            ),
            Place(
                1,
                "Example Place",
                "Example Type",
                "Example Address",
                "Example City",
                "Example Postal Code",
                0.0,
                0.0,
                "Example Description",
                "anomalieArt.jpg"
            )
        ),
        PlaceReviewWithDetails(
            PlaceReview(
                2,
                1,
                2,
                4,
                "Nice place!",
                mockDate
            ),
            User(
                2,
                "Anna Müller",
                "anna.mueller@example.com",
                "password",
                false,
                image = "anomalieArt.jpg"
            ),
            Place(
                1,
                "Example Place",
                "Example Type",
                "Example Address",
                "Example City",
                "Example Postal Code",
                0.0,
                0.0,
                "Example Description",
                "anomalieArt.jpg"
            )
        )
    )

    FlavourTrail_v2Theme {
        ReviewScreen(
            reviews = exampleReviews,
            placeId = 1
        )
    }
}