package com.example.flavourtrail_v2

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.example.flavourtrail_v2.data.AppDatabase
import com.example.flavourtrail_v2.data.ViewModel.ReviewViewModelFactory
import com.example.flavourtrail_v2.data.repository.PlaceReviewRepository
import com.example.flavourtrail_v2.ui.components.review.ReviewScreen

class ReviewActivity : BaseActivity() {
    private val reviewViewModel: PlaceReviewViewModel by viewModels {
        ReviewViewModelFactory(
            PlaceReviewRepository(
                AppDatabase.getInstance(application).placeReviewDao()
            )
        )
    }

    @Composable
    override fun Content() {
        val placeId = intent.getIntExtra("PLACE_ID", 1) // Default to 1 if not provided
        ReviewScreen(
            placeId = placeId,
            reviewViewModel = reviewViewModel
        )
    }
}
