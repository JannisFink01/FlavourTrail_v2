package com.example.flavourtrail_v2.ui.components.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(items: List<String>, onItemSelected: (String) -> Unit) {
    val currentIndex = remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center
    ) {
        Row() {
            val stopCounter = 1
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft, // Use a built-in Material Icon
                contentDescription = "Stop Count", // Provide a description for accessibility
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        if (currentIndex.value > 0) {
                            currentIndex.value--
                            onItemSelected(items[currentIndex.value])
                        }
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$stopCounter/5")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight, // Use a built-in Material Icon
                contentDescription = "Stop Count", // Provide a description for accessibility
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        if (currentIndex.value < items.size - 1) {
                            currentIndex.value++
                            onItemSelected(items[currentIndex.value])
                        }
                    }
            )
        }
    }
}
