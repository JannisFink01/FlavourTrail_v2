import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flavourtrail_v2.data.entity.*
import com.example.flavourtrail_v2.data.entity.PlaceReviewWithDetails
import com.example.flavourtrail_v2.ui.components.review.ReviewCard
import com.example.flavourtrail_v2.ui.theme.FlavourTrail_v2Theme
import java.text.SimpleDateFormat
import java.util.Locale

enum class SortOption {
    DATE_ASCENDING,
    DATE_DESCENDING,
    RATING_ASCENDING,
    RATING_DESCENDING
}

@Composable
fun ReviewScreen(
    reviews: List<PlaceReviewWithDetails>,
    placeId: Int,
    modifier: Modifier = Modifier
) {
    var selectedSortOption by remember { mutableStateOf(SortOption.DATE_DESCENDING) }

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Reviews for Place ID: $placeId", style = MaterialTheme.typography.titleMedium)
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
                    text = { Text("Rating Ascending") }, // Provide a composable lambda
                    onClick = {
                        selectedSortOption = SortOption.RATING_ASCENDING
                        expandedRating = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Rating Descending") }, // Provide a composable lambda
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