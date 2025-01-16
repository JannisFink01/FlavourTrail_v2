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