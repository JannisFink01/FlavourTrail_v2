//package com.example.flavourtrail_v2
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.activity.viewModels
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.flavourtrail_v2.data.AppDatabase
//import com.example.flavourtrail_v2.data.repository.PlaceReviewRepository
//import com.example.flavourtrail_v2.ui.theme.FlavourTrail_v2Theme
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//class RviewActivity : ComponentActivity() {
//    private val placeReviewViewModel: PlaceReviewViewModel by viewModels {
//        val database = AppDatabase.getInstance(applicationContext)
//        val repository = PlaceReviewRepository(database.placeReviewDao())
//        PlaceReviewViewModelFactory(repository)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            FlavourTrail_v2Theme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    ReviewScreen(
//                        placeReviewViewModel = placeReviewViewModel,
//                        placeId = 1, // Replace with the actual place ID
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ReviewScreen(
//    placeReviewViewModel: PlaceReviewViewModel,
//    placeId: Int,
//    modifier: Modifier = Modifier
//) {
//    val reviews by placeReviewViewModel.reviews.collectAsState()
//
//    LaunchedEffect(placeId) {
//        placeReviewViewModel.getReviewsByPlaceId(placeId)
//    }
//
//    Column(modifier = modifier.padding(16.dp)) {
//        Text(text = "Reviews for Place ID: $placeId", style = MaterialTheme.typography.h6)
//        Spacer(modifier = Modifier.height(8.dp))
//        reviews.forEach { review ->
//            Text(text = "Rating: ${review.placeReview.rating}")
//            Text(text = "Comment: ${review.comment}")
//            Spacer(modifier = Modifier.height(8.dp))
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ReviewScreenPreview() {
//    FlavourTrail_v2Theme {
//        ReviewScreen(
//            placeReviewViewModel = viewModel(),
//            placeId = 1
//        )
//    }
//}