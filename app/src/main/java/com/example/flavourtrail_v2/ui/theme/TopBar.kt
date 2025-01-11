package com.example.flavourtrail_v2.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import com.example.flavourtrail_v2.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp


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
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.weight(1f))

                // Benutzername
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Profilbild
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

