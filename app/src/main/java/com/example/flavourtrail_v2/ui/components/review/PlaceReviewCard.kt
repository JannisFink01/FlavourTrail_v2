package com.example.flavourtrail_v2.ui.components.review

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flavourtrail_v2.R
import com.example.flavourtrail_v2.data.entity.PlaceReviewWithDetails
import com.example.flavourtrail_v2.ui.StarRatingBar
import com.example.flovourtrail_v1.database.entity.Place
import com.example.flovourtrail_v1.database.entity.PlaceReview
import com.example.flovourtrail_v1.database.entity.User

class PlaceReviews : ComponentActivity() {
    public val exampleReview = PlaceReviewWithDetails(
        PlaceReview(1, 1, 1, 5, "Great place!"), User(
            1, "Thorsten Schmitz", "thomas.r.marshall@example.com", "password", false
        ), Place(
            1,
            "Example Place",
            "Example Type",
            "Example Address",
            "Example City",
            "Example Postal Code",
            0.0,
            0.0,
            "Example Description"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReviewCard(
                review = exampleReview, profileImageRes = R.drawable.profile_user
            )
        }

    }
}


@Composable
fun ReviewCard(review: PlaceReviewWithDetails, profileImageRes: Int) {
    Card(
        modifier = Modifier
            .padding(8.dp)
//            .background(MaterialTheme.colorScheme.surface)

            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = profileImageRes),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = review.user.name, style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = review.place.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            StarRatingBar(maxStars = 5, rating = review.placeReview.rating.toFloat())
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = review.placeReview.comment, style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
fun PreviewHeadline() {
    val exampleReview = PlaceReviewWithDetails(
        PlaceReview(1, 1, 1, 5, "Great place!"), User(
            1, "Thorsten Schmitz", "thomas.r.marshall@example.com", "password", false
        ), Place(
            1,
            "Example Place",
            "Example Type",
            "Example Address",
            "Example City",
            "Example Postal Code",
            0.0,
            0.0,
            "Example Description"
        )
    )
    ReviewCard(
        review = exampleReview, profileImageRes = R.drawable.profile_user
    )
}