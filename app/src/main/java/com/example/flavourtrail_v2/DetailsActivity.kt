package com.example.flavourtrail_v2

import android.content.Intent
import android.os.Bundle
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
                    placeId = intent.getIntExtra("PLACE_ID", 1), // Default to 1 if not provided
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
    var place by remember { mutableStateOf<Place?>(null) }
    val placeWithDetails by placeReviewViewModel.reviews.collectAsState()
    LaunchedEffect(placeId) {
        place = placeViewModel.getPlaceById(placeId)
        placeReviewViewModel.getReviewsWithDetailsByPlaceId(placeId)
    }
    val averageRating = placeWithDetails.map { it.placeReview.rating }.average().toFloat()

    // Create a scrollable state for vertical scrolling
    val scrollState = rememberScrollState() // State to control scrolling
    Scaffold(
        topBar = {
            TopBar(
                userName = "Max Mustermann", // Beispiel-Benutzername
                profileImageRes = R.drawable.profile_user
            )
        },
        bottomBar = {
            NavigationBar {
                BottomNavigationBar()
            }
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState) // Enable scrolling
        ) {
            InteractionBar()
            ImageSection(place = place)
            TitleSection(place = place, modifier = Modifier.padding(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StarRatingSection(averageRating = averageRating)
                PriceInformationSection()
            }
            Box(
                Modifier.align(Alignment.Start)
            ) {
                ViewReviewsButton(placeId = placeId)
            }
            BookNowButton("Book Now")
            Row(modifier = Modifier.padding(16.dp)) {
                TimeInformationSection()
                Spacer(modifier = Modifier.width(16.dp))
                RateDestinationButton()
            }
            place?.let {
                DetailSection(place = it)
            }
        }
    }
}


@Composable
fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft, // Use a built-in Material Icon
                contentDescription = "Stop Count", // Provide a description for accessibility
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "1/5")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight, // Use a built-in Material Icon
                contentDescription = "Stop Count", // Provide a description for accessibility
                modifier = Modifier.size(24.dp),
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.map_icon), // Use a built-in Material Icon
            contentDescription = "Map Icon", // Provide a description for accessibility
            modifier = Modifier
                .size(24.dp)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Filled.LocationOn, // Use a built-in Material Icon ,
            contentDescription = "Distance",
            modifier = Modifier.weight(1f)
        )
    }
}

//Definitionen der einzelnen Composables
@Composable
fun InteractionBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Use a built-in Material Icon
            contentDescription = "ArrowBack Icon",
            modifier = Modifier.size(48.dp) // Optional: Size of the icon
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Filled.Share, // Use a built-in Material Icon
            contentDescription = "Share Icon",
            modifier = Modifier.size(48.dp) // Optional: Size of the icon
        )
        HeartToggleIcon(
            iconSize = 48.dp,
            contentDescription = "Toggle Heart Icon"
        )
    }
}

@Composable
fun ImageSection(place: Place? = null) {
    val context = LocalContext.current
    val imageId =
        context.resources.getIdentifier(place?.image, "drawable", context.packageName)
    val imagePainter = if (imageId != 0) {
        painterResource(id = imageId)
    } else {
        painterResource(id = R.drawable.destination_placeholder)
    }
    Image(
        painter = imagePainter,
        contentDescription = "Image",
        modifier = Modifier.fillMaxWidth()
    )
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
fun ViewReviewsButton(placeId: Int, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Row(
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Star Icon",
            modifier = Modifier.size(20.dp)
        )
        TextAsButton(
            text = "View Reviews",
            onClick = {
                val intent = Intent(context, ReviewActivity::class.java).apply {
                    putExtra("PLACE_ID", placeId)
                }
                context.startActivity(intent)
            }
        )
    }
}

@Composable
fun BookNowButton(buttonText: String) {
    Button(
        onClick = { /* Handle button click */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            //containerColor = Color.Blue,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = buttonText,
            fontSize = 20.sp
        )
    }
}

@Composable
fun TimeInformationSection() {
    Column {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.clock_icon), // Use a built-in Material Icon
                contentDescription = "Clock Icon", // Provide a description for accessibility
                modifier = Modifier.size(24.dp), // Adjust size if needed
                tint = Color.Black // Optional: Set a tint color
            )
            Text(
                text = "Opening Hours",
                fontSize = 20.sp
            )
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.hourglass_icon), // Use a custom icon
                contentDescription = "Hourglass Icon", // Provide a description for accessibility
                modifier = Modifier.size(24.dp), // Adjust size if needed
            )
            Text(
                text = "Time spent",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun RateDestinationButton() {
    Button(
        onClick = { /* Handle button click */ },
        modifier = Modifier.width(200.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Star, // Use a built-in Material Icon
            contentDescription = "Star Icon", // Provide a description for accessibility
            modifier = Modifier.size(24.dp), // Adjust size if needed
            tint = Color.DarkGray // Optional: Set a tint color
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Rate Destination",
            fontSize = 20.sp
        )
    }
}

@Composable
fun DetailSection(place: Place) {


    // Box to hold the content with a background color
    Box(
        modifier = Modifier
            .fillMaxSize() // Occupies the entire screen
            .background(Color.LightGray) // Background color
    ) {
        // Text content inside the Box
        Text(
            text = place.description,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp) // Padding around the text
        )
    }
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

@Composable
fun HeartToggleIcon(
    iconSize: Dp = 48.dp, // Parameter to control the size of the icon
    contentDescription: String = "Toggle Heart Icon" // Parameter for accessibility
) {
    // MutableState to track the heart's state
    var isHeartSelected by remember { mutableStateOf(false) }

    // Clickable Icon
    Icon(
        imageVector = if (isHeartSelected) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
        contentDescription = contentDescription, // Use parameter
        modifier = Modifier
            .size(iconSize) // Use parameter for size
            .clickable { isHeartSelected = !isHeartSelected }, // Toggle the state on click
        tint = if (isHeartSelected) Color.Red else Color.Black // Optional: Change tint color
    )
}