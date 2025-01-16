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

@Composable
fun InteractionBar() {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Use a built-in Material Icon
            contentDescription = "ArrowBack Icon",
            modifier = Modifier
                .size(48.dp) // Optional: Size of the icon
                .clickable {
                    // Handle the back navigation
                    val activity = (context as? Activity)
                    activity?.finish()
                }
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
