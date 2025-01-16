package com.example.flavourtrail_v2

import android.content.Intent
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavourtrail_v2.data.AppDatabase
import com.example.flavourtrail_v2.data.ViewModel.*
import com.example.flavourtrail_v2.data.entity.*
import com.example.flavourtrail_v2.data.repository.*
import com.example.flavourtrail_v2.ui.theme.FlavourTrail_v2Theme
import com.example.flavourtrail_v2.ui.TopBar
import com.example.flavourtrail_v2.ui.components.ImageSection
import com.example.flavourtrail_v2.ui.components.details.BookNowButton
import com.example.flavourtrail_v2.ui.components.details.BottomNavigationBar
import com.example.flavourtrail_v2.ui.components.details.DetailSection
import com.example.flavourtrail_v2.ui.components.details.InteractionBar
import com.example.flavourtrail_v2.ui.components.details.RateDestinationButton
import com.example.flavourtrail_v2.ui.components.details.TimeInformationSection
import com.example.flavourtrail_v2.ui.components.details.ViewReviewsButton
import com.example.flavourtrail_v2.ui.components.review.RatingPopup
import com.example.flavourtrail_v2.ui.components.review.StarRatingBar

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

@Composable
fun DetailScreen(
    placeId: Int,
    placeViewModel: PlaceViewModel,
    placeReviewViewModel: PlaceReviewViewModel,
    modifier: Modifier = Modifier
) {
    val placeWithDetails by placeReviewViewModel.reviews.collectAsState()
    var place by remember { mutableStateOf<Place?>(null) }
    LaunchedEffect(placeId) {
        place = placeViewModel.getPlaceById(placeId)
        placeReviewViewModel.getReviewsWithDetailsByPlaceId(placeId)
    }
    val averageRating = placeWithDetails.map { it.placeReview.rating }.average().toFloat()

    // Create a scrollable state for vertical scrolling
    val scrollState = rememberScrollState() // State to control scrolling
    FlavourTrail_v2Theme {
        Scaffold(
            topBar = {
                TopBar(
                    userName = "Max Mustermann", // Beispiel-Benutzername
                    profileImageRes = R.drawable.profile_user
                )
            },
            bottomBar = {
                FlavourTrail_v2Theme {
                    NavigationBar {
                        BottomNavigationBar()
                    }
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
                            PriceInformationSection()
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


@Composable
fun TitleSection(place: Place? = null, modifier: Modifier = Modifier) {
    Text(
        text = place?.name ?: "Placeholder",
        fontSize = 24.sp,
        modifier = modifier
    )
}

@Composable
fun StarRatingSection(averageRating: Float, modifier: Modifier = Modifier) {
    StarRatingBar(5, averageRating, false)
}

@Composable
fun PriceInformationSection(modifier: Modifier = Modifier) {
    Text(
        text = "35,99€",
        fontSize = 20.sp,
        modifier = modifier
    )
}

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
            onClick()  // Trigger the navigation or action
        }
    )
}