package com.example.flavourtrail_v2.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.flavourtrail_v2.R
import com.example.flavourtrail_v2.data.entity.Place

@Composable
fun ImageSection(place: Place) {
    val context = LocalContext.current
    val imageId = context.resources.getIdentifier(place?.image, "drawable", context.packageName)
    val imagePainter = if (imageId != 0) {
        painterResource(id = imageId)
    } else {
        Log.e("ImageSection", "Invalid image resource: ${place?.image}")
        painterResource(id = R.drawable.destination_placeholder)
    }
    // Handle the case where the image resource is not found
    Image(
        painter = imagePainter,
        contentDescription = "Placeholder Image",
        modifier = Modifier.fillMaxWidth()
    )

}