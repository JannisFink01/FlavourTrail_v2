package com.example.flavourtrail_v2.ui.components.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


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
