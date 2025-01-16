package com.example.flavourtrail_v2.ui.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavourtrail_v2.data.entity.Place

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