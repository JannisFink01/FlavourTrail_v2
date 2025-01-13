package com.example.flavourtrail_v1.ui.theme

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
                // App-Logo (ganz links)
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Fit
                )

                // Platzhalter zwischen Logo und Benutzername
                Spacer(modifier = Modifier.weight(1f))

                // Benutzername (links neben Profilbild)
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp
                )

                // Abstand zwischen Benutzername und Profilbild
                Spacer(modifier = Modifier.width(8.dp))

                // Profilbild (ganz rechts)
                Image(
                    painter = painterResource(id = profileImageRes),
                    contentDescription = "Profilbild",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

