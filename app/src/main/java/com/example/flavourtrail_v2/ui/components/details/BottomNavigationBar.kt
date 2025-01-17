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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A composable function for displaying a bottom navigation bar with controls to navigate stops.
 *
 * This navigation bar includes left and right navigation icons with a text indicator to show
 * the current stop number out of a total number of stops. The icons are clickable and allow for
 * interaction to move between stops.
 */
@Composable
fun BottomNavigationBar(index: Int,items: List<String>, onItemSelected: (String) -> Unit) {
    var index by remember { mutableIntStateOf(index) }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center
    ) {
        Row {
            val stopCounter = 1 // Current stop indicator
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft, // Left navigation icon
                contentDescription = "Previous Stop", // Description for accessibility
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        if (index > 0) {
                            index --
                        } else {
                            index = items.size - 1
                        }
                        onItemSelected(items[index])
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "${index +1}/${items.size}")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight, // Right navigation icon
                contentDescription = "Next Stop", // Description for accessibility
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        if (index < items.size - 1) {
                            index++
                        } else {
                            index = 0
                        }
                        onItemSelected(items[index])
                    }
            )
        }
    }
}
