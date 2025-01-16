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

/**
 * A composable function for displaying a toggleable heart icon.
 *
 * This icon allows users to toggle between a selected (filled heart) and unselected (outlined heart) state.
 * The icon size and content description are configurable via parameters.
 *
 * @param iconSize The size of the heart icon. Default is 48.dp.
 * @param contentDescription Accessibility description for the icon. Default is "Toggle Heart Icon".
 */
@Composable
fun HeartToggleIcon(
    iconSize: Dp = 48.dp, // Parameter to control the size of the icon
    contentDescription: String = "Toggle Heart Icon" // Parameter for accessibility
) {
    // Mutable state to track the selection state of the heart icon
    var isHeartSelected by remember { mutableStateOf(false) }

    // Render the clickable heart icon
    Icon(
        imageVector = if (isHeartSelected) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
        contentDescription = contentDescription, // Accessibility description
        modifier = Modifier
            .size(iconSize) // Apply the size parameter
            .clickable { isHeartSelected = !isHeartSelected }, // Toggle state on click
        tint = if (isHeartSelected) Color.Red else Color.Black // Change color based on state
    )
}
