package com.example.flavourtrail_v2.ui.components.details

import android.content.Intent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.flavourtrail_v2.ReviewActivity
import com.example.flavourtrail_v2.TextAsButton

/**
 * A composable function that displays a button for viewing reviews of a specific place.
 *
 * This button consists of an arrow icon and a clickable text. When clicked, it navigates to the `ReviewActivity`
 * with the provided place ID passed as an extra.
 *
 * @param placeId The unique identifier of the place for which reviews are to be viewed.
 * @param modifier An optional [Modifier] to customize the layout of the button.
 */
@Composable
fun ViewReviewsButton(placeId: Int, modifier: Modifier = Modifier) {
    val context = LocalContext.current // Retrieve the current context

    Row(
        modifier = modifier
    ) {
        // Icon displayed to the left of the text
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight, // Arrow icon
            contentDescription = "Arrow Icon", // Accessibility description
            modifier = Modifier.size(20.dp) // Size of the icon
        )
        // Clickable text to view reviews
        TextAsButton(
            text = "View Reviews", // Button label
            onClick = {
                // Navigate to the ReviewActivity with the place ID
                val intent = Intent(context, ReviewActivity::class.java).apply {
                    putExtra("PLACE_ID", placeId)
                }
                context.startActivity(intent)
            }
        )
    }
}
