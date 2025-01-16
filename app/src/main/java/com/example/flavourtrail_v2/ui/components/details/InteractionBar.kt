package com.example.flavourtrail_v2.ui.components.details

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * A composable function for displaying an interaction bar with navigation, sharing, and a favorite toggle option.
 *
 * The interaction bar contains:
 * - A back arrow icon to navigate to the previous screen.
 * - A share icon for sharing content.
 * - A toggleable heart icon for marking items as favorites.
 */
@Composable
fun InteractionBar() {
    val context = LocalContext.current // Retrieve the current context

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        // Back arrow icon
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Back navigation icon
            contentDescription = "Back Icon", // Accessibility description
            modifier = Modifier
                .size(48.dp) // Size of the back icon
                .clickable {
                    // Finish the current activity to navigate back
                    val activity = (context as? Activity)
                    activity?.finish()
                }
        )

        // Spacer to align elements properly
        Spacer(modifier = Modifier.weight(1f))

        // Share icon
        Icon(
            imageVector = Icons.Filled.Share, // Share icon
            contentDescription = "Share Icon", // Accessibility description
            modifier = Modifier.size(48.dp) // Size of the share icon
        )

        // Heart toggle icon for marking as favorite
        HeartToggleIcon(
            iconSize = 48.dp,
            contentDescription = "Toggle Heart Icon"
        )
    }
}
