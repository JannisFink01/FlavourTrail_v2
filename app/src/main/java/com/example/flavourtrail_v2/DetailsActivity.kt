package com.example.flavourtrail_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavourtrail_v2.data.AppDatabase
import com.example.flavourtrail_v2.data.ViewModel.PlaceViewModel
import com.example.flavourtrail_v2.data.ViewModel.PlaceViewModelFactory
import com.example.flavourtrail_v2.data.ViewModel.ReviewViewModelFactory
import com.example.flavourtrail_v2.data.entity.Place
import com.example.flavourtrail_v2.data.repository.PlaceRepository
import com.example.flavourtrail_v2.data.repository.PlaceReviewRepository
import com.example.flavourtrail_v2.ui.TopBar
import com.example.flavourtrail_v2.ui.components.ImageSection
import com.example.flavourtrail_v2.ui.components.details.BookNowButton
import com.example.flavourtrail_v2.ui.components.details.BottomNavigationBar
import com.example.flavourtrail_v2.ui.components.details.DetailSection
import com.example.flavourtrail_v2.ui.components.details.InteractionBar
import com.example.flavourtrail_v2.ui.components.details.RateDestinationButton
import com.example.flavourtrail_v2.ui.components.details.TimeInformationSection
import com.example.flavourtrail_v2.ui.components.details.ViewReviewsButton
import com.example.flavourtrail_v2.ui.components.review.StarRatingBar
import com.example.flavourtrail_v2.ui.theme.FlavourTrail_v2Theme

/**
 * Activity for displaying the details of a place.
 * This activity sets up the UI and interacts with ViewModels to fetch and display place details.
 */
class DetailsActivity : ComponentActivity() {
    private val placeViewModel: PlaceViewModel by viewModels {
        PlaceViewModelFactory(
            PlaceRepository(
                AppDatabase.getInstance(application).placeDao()
            )
        )
    }
    private val placeReviewViewModel: PlaceReviewViewModel by viewModels {
        ReviewViewModelFactory(
            PlaceReviewRepository(
                AppDatabase.getInstance(application).placeReviewDao()
            )
        )
    }

    /**
     * Initializes the activity, enabling edge-to-edge UI and setting the content.
     *
     * @param savedInstanceState The saved state of the activity, if any.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlavourTrail_v2Theme {
                DetailScreen(
                    placeId = intent.getIntExtra("PLACE_ID", 2), // Default to 1 if not provided
                    placeViewModel = placeViewModel,
                    placeReviewViewModel = placeReviewViewModel
                )
            }
        }
    }
}

/**
 * Composable function to display the details of a place.
 *
 * @param placeId The ID of the place to display.
 * @param placeViewModel The ViewModel for fetching place data.
 * @param placeReviewViewModel The ViewModel for fetching reviews of the place.
 */
@Composable
fun DetailScreen(
    placeId: Int,
    placeViewModel: PlaceViewModel,
    placeReviewViewModel: PlaceReviewViewModel,
) {
    val placeWithDetails by placeReviewViewModel.reviews.collectAsState()
    var place by remember { mutableStateOf<Place?>(null) }
    LaunchedEffect(placeId) {
        place = placeViewModel.getPlaceById(placeId)
        placeReviewViewModel.getReviewsWithDetailsByPlaceId(placeId)
    }
    val averageRating = placeWithDetails.map { it.placeReview.rating }.average().toFloat()

    FlavourTrail_v2Theme {
        Scaffold(
            topBar = {
                TopBar(
                    userName = "John Doe", // Beispiel-Benutzername
                    profileImageRes = R.drawable.profile_user
                )
            },
            bottomBar = {
                NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
                    BottomNavigationBar()
                }
            }
        )
        { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                InteractionBar()
                LazyColumn {
                    item {
                        place?.let { ImageSection(place = it) }
                    }
                    item {
                        TitleSection(place = place, modifier = Modifier.padding(16.dp))
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            StarRatingSection(averageRating = averageRating)
                        }
                    }
                    item {
                        Box(
                            Modifier.align(Alignment.Start)
                        ) {
                            ViewReviewsButton(placeId = placeId)
                        }
                    }
                    item {
                        BookNowButton("Book Now")
                    }
                    item {
                        Row(modifier = Modifier.padding(16.dp)) {
                            TimeInformationSection()
                            Spacer(modifier = Modifier.width(16.dp))
                            RateDestinationButton(placeReviewViewModel, placeId)
                        }
                    }
                    item {
                        place?.let {
                            DetailSection(place = it)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Composable function to display the title section of a place.
 *
 * @param place The place whose title is to be displayed.
 * @param modifier Optional [Modifier] for this composable.
 */
@Composable
fun TitleSection(place: Place? = null, modifier: Modifier = Modifier) {
    Text(
        text = place?.name ?: "Placeholder",
        fontSize = 24.sp,
        modifier = modifier
    )
}

/**
 * Composable function to display the star rating section.
 *
 * @param averageRating The average rating of the place.
 * @param modifier Optional [Modifier] for this composable.
 */
@Composable
fun StarRatingSection(averageRating: Float, modifier: Modifier = Modifier) {
    StarRatingBar(5, averageRating, false)
}

/**
 * Composable function to display text as a clickable button.
 *
 * @param onClick The action to perform when the text is clicked.
 * @param text The text to display.
 * @param fontSize The font size of the text.
 */
@Composable
fun TextAsButton(
    onClick: () -> Unit,
    text: String = "Clickable Text",
    fontSize: androidx.compose.ui.unit.TextUnit = 20.sp
) {
    Text(
        text = text,
        fontSize = fontSize,
        modifier = Modifier.clickable {
            onClick()
        }
    )
}