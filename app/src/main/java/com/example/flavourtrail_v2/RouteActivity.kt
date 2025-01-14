package com.example.flavourtrail_v2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue


class RouteActivity : BaseActivity() {
    @Composable
    override fun Content() {
        RouteScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteScreen() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Removed the title text */ },
                modifier = Modifier.fillMaxWidth()
            )
        },
        bottomBar = {
            // Button Bar at the bottom
            ButtonBar(
                onSaveClick = { /* Handle Save Click */ },
                onNavigateClick = { /* Handle Navigate Click */ },
                onAddLocationClick = { /* Handle Add Location Click */ }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Integrated Search Bar with Buttons placed directly below the TopAppBar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Search Bar
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { newText -> searchText = newText },
                    label = { Text("Search") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search), // Replace with your search icon
                            contentDescription = "Search Icon"
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .height(42.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )

                // Right Buttons
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = { /* Handle Right Button 1 Click */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fullscreen), // Replace with your icon
                            contentDescription = "Fullscreen Icon"
                        )
                    }

                    IconButton(onClick = { /* Handle Right Button 2 Click */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share), // Replace with your icon
                            contentDescription = "Share Icon"
                        )
                    }

                    IconButton(onClick = { /* Handle Right Button 3 Click */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_download), // Replace with your icon
                            contentDescription = "Download Icon"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Other content can go here (e.g., list of routes or whatever content you need)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ButtonBar(
    onSaveClick: () -> Unit,
    onNavigateClick: () -> Unit,
    onAddLocationClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Save Button
        Button(
            onClick = onSaveClick,
            modifier = Modifier.weight(1f) // Equal width
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_save), // Replace with your icon
                contentDescription = "Save Icon",
                modifier = Modifier.size(20.dp) // Adjust size as needed
            )
            Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
            Text(
                text = "Save",
                maxLines = 1,
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )
        }

        // Navigate Button
        Button(
            onClick = onNavigateClick,
            modifier = Modifier.weight(1f) // Equal width
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_navigate), // Replace with your icon
                contentDescription = "Navigate Icon",
                modifier = Modifier.size(20.dp) // Adjust size as needed
            )
            Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
            Text(
                text = "Navigate",
                maxLines = 1,
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )
        }

        // Add Location Button
        Button(
            onClick = onAddLocationClick,
            modifier = Modifier.weight(1f) // Equal width
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_location), // Replace with your icon
                contentDescription = "Add Location Icon",
                modifier = Modifier.size(20.dp) // Adjust size as needed
            )
            Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
            Text(
                text = "Add Location",
                maxLines = 1,
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )
        }
    }
}
