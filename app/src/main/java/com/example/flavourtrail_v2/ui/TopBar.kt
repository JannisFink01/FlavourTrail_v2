package com.example.flavourtrail_v2.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavourtrail_v2.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(userName: String, profileImageRes: Int) {
    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(35.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.weight(1f))

                // Benutzername
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp,
                    color = Color.White // Text in Weiß
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Profilbild
                Image(
                    painter = painterResource(id = profileImageRes),
                    contentDescription = "Profilbild",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp),
                    colorFilter = ColorFilter.tint(Color.White) // Bild in Weiß einfärben
                )

            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

