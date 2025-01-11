package com.example.flavourtrail_v2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flavourtrail_v2.ui.theme.FlavourTrail_v2Theme
import com.example.flavourtrail_v2.ui.TopBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlavourTrail_v2Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar(
                            userName = "Max Mustermann",
                            profileImageRes = R.drawable.profile_picture
                        )
                    }
                ) { innerPadding ->
                    PlanYourRouteButton(
                        modifier = Modifier.padding(innerPadding),
                        onClick = {
                            // Starte die PlanRouteActivity
                            val intent = Intent(this, PlanRouteActivity::class.java)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PlanYourRouteButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .padding(top = 32.dp)
        ) {
            Text(text = "Plan your Route")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanYourRouteButtonPreview() {
    FlavourTrail_v2Theme {
        Scaffold(
            topBar = {
                TopBar(
                    userName = "Preview User",
                    profileImageRes = R.drawable.profile_picture
                )
            }
        ) {
            PlanYourRouteButton(
                modifier = Modifier.padding(it),
                onClick = { /* Keine Aktion in der Preview */ }
            )
        }
    }
}
