package com.example.flavourtrail_v2

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.example.flavourtrail_v2.data.AppDatabase
import com.example.flavourtrail_v2.data.ViewModel.ReviewViewModelFactory
import com.example.flavourtrail_v2.data.repository.PlaceReviewRepository
import com.example.flavourtrail_v2.ui.components.review.ReviewScreen

/**
 * Activity for displaying reviews of a place.
 * This activity sets up the UI and interacts with the [PlaceReviewViewModel] to fetch and display reviews.
 */
class ReviewActivity : BaseActivity() {
    /**
     * ViewModel for managing place reviews. Initialized using a factory with a repository.
     */
    private val reviewViewModel: PlaceReviewViewModel by viewModels {
        ReviewViewModelFactory(
            PlaceReviewRepository(
                AppDatabase.getInstance(application).placeReviewDao()
            )
        )
    }

    /**
     * Composable function that provides the content for the activity.
     * Displays the [ReviewScreen] for a specific place based on the `PLACE_ID` provided in the intent.
     */
    @Composable
    override fun Content() {
        val placeId = intent.getIntExtra("PLACE_ID", 1) // Default to 1 if not provided
        ReviewScreen(
            placeId = placeId,
            reviewViewModel = reviewViewModel
        )
    }
}
