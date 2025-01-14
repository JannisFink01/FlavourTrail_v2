package com.example.flavourtrail_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.flavourtrail_v2.PlaceReviewViewModel
import com.example.flavourtrail_v2.data.AppDatabase
import com.example.flavourtrail_v2.data.ViewModel.ReviewViewModelFactory
import com.example.flavourtrail_v2.data.entity.*
import com.example.flavourtrail_v2.data.repository.PlaceReviewRepository
import com.example.flavourtrail_v2.ui.components.review.ReviewScreen
import com.example.flavourtrail_v2.ui.theme.FlavourTrail_v2Theme
import java.text.SimpleDateFormat
import java.util.Locale

class ReviewActivity : ComponentActivity() {
    private val reviewViewModel: PlaceReviewViewModel by viewModels {
        ReviewViewModelFactory(PlaceReviewRepository(AppDatabase.getInstance(application).placeReviewDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val placeId = intent.getIntExtra("PLACE_ID", 1) // Default to 1 if not provided
        setContent {
            FlavourTrail_v2Theme {
                ReviewScreen(
                    placeId = placeId,
                    reviewViewModel = reviewViewModel
                )
            }
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

//    FlavourTrail_v2Theme {
//        ReviewScreen(
//            placeId = 1,
//            reviewViewModel = null
//        )
//    }
}