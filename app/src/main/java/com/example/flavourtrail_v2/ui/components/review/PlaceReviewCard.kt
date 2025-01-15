package com.example.flavourtrail_v2.ui.components.review

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.flavourtrail_v2.R
import com.example.flavourtrail_v2.data.entity.PlaceReviewWithDetails
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ReviewCard(review: PlaceReviewWithDetails) {
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    val formattedDate = dateFormat.format(review.placeReview.date)
    val imageResId =
        context.resources.getIdentifier(review.user.image, "drawable", context.packageName)
    val imagePainter = if (imageResId != 0) {
        painterResource(id = imageResId)
    } else {
        Log.e("ReviewCard", "Invalid image resource: ${review.user.image}")
        painterResource(id = R.drawable.profile_user) // Ein Fallback-Bild
    }

    Card(
        modifier = Modifier
            .padding(8.dp)

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
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
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
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = formattedDate, style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            StarRatingBar(
                maxStars = 5,
                initialRating = review.placeReview.rating.toFloat(),
                isChangeable = false
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = review.placeReview.comment, style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
