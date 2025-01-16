package com.example.flavourtrail_v2.ui.components.review

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.flavourtrail_v2.PlaceReviewViewModel

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
    var expandedSort by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(modifier = modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Use a built-in Material Icon
                contentDescription = "ArrowBack Icon",
                modifier = Modifier
                    .size(48.dp) // Optional: Size of the icon
                    .clickable {
                        // Handle the back navigation
                        val activity = (context as? Activity)
                        activity?.finish()
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Reviews",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = { expandedSort = true }) {
                Text("Sort by")
                Icon(
                    painter = painterResource(id = android.R.drawable.arrow_down_float),
                    contentDescription = "Dropdown Icon"
                )
            }
            DropdownMenu(
                expanded = expandedSort,
                onDismissRequest = { expandedSort = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Date Ascending") },
                    onClick = {
                        selectedSortOption = SortOption.DATE_ASCENDING
                        expandedSort = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Date Descending") },
                    onClick = {
                        selectedSortOption = SortOption.DATE_DESCENDING
                        expandedSort = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Rating Ascending") },
                    onClick = {
                        selectedSortOption = SortOption.RATING_ASCENDING
                        expandedSort = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Rating Descending") },
                    onClick = {
                        selectedSortOption = SortOption.RATING_DESCENDING
                        expandedSort = false
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

        LazyColumn {
            items(sortedReviews.size) { index ->
                ReviewCard(review = sortedReviews[index])
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}