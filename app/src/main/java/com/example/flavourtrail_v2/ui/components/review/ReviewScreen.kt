// ReviewScreen.kt
package com.example.flavourtrail_v2.ui.components.review

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flavourtrail_v2.PlaceReviewViewModel
import com.example.flavourtrail_v2.data.entity.*

enum class SortOption {
    DATE_ASCENDING,
    DATE_DESCENDING,
    RATING_ASCENDING,
    RATING_DESCENDING
}

@Composable
fun ReviewScreen(
    placeId: Int,
    reviewViewModel: PlaceReviewViewModel,
    modifier: Modifier = Modifier
) {
    val reviews by reviewViewModel.reviews.collectAsState()

    LaunchedEffect(placeId) {
        reviewViewModel.getReviewsByPlaceId(placeId)
    }

    var selectedSortOption by remember { mutableStateOf(SortOption.DATE_DESCENDING) }
    LazyColumn(modifier = modifier.padding(16.dp)) {
        item {
            Text(
                text = "Reviews for Place ID: $placeId",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown for sorting by date
            var expandedDate by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = { expandedDate = true }) {
                    Text("Sort by Date")
                }
                DropdownMenu(
                    expanded = expandedDate,
                    onDismissRequest = { expandedDate = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Date Ascending") },
                        onClick = {
                            selectedSortOption = SortOption.DATE_ASCENDING
                            expandedDate = false
                        })
                    DropdownMenuItem(
                        text = { Text("Date Descending") },
                        onClick = {
                            selectedSortOption = SortOption.DATE_DESCENDING
                            expandedDate = false
                        })
                }
            }

            // Dropdown for sorting by rating
            var expandedRating by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = { expandedRating = true }) {
                    Text("Sort by Rating")
                }
                DropdownMenu(
                    expanded = expandedRating,
                    onDismissRequest = { expandedRating = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Rating Ascending") },
                        onClick = {
                            selectedSortOption = SortOption.RATING_ASCENDING
                            expandedRating = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Rating Descending") },
                        onClick = {
                            selectedSortOption = SortOption.RATING_DESCENDING
                            expandedRating = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val sortedReviews = when (selectedSortOption) {
                SortOption.DATE_ASCENDING -> reviews.sortedBy { it.placeReview.date }
                SortOption.DATE_DESCENDING -> reviews.sortedByDescending { it.placeReview.date }
                SortOption.RATING_ASCENDING -> reviews.sortedBy { it.placeReview.rating }
                SortOption.RATING_DESCENDING -> reviews.sortedByDescending { it.placeReview.rating }
            }

            sortedReviews.forEach { review ->
                ReviewCard(review = review)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}