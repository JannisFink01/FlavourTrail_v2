package com.example.flavourtrail_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flavourtrail_v2.ui.TopBar
import com.example.flavourtrail_v2.ui.theme.FlavourTrail_v2Theme

abstract class BaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlavourTrail_v2Theme {
                BaseLayout { Content() }
            }
        }
    }

    // Abstrakte Methode, die jede Activity definiert
    @Composable
    abstract fun Content()
}

@Composable
fun BaseLayout(content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            userName = "John Doe", // Beispiel für Benutzernamen
            profileImageRes = R.drawable.profile_user // Beispiel für Profilbild
        )

        Spacer(modifier = Modifier.height(16.dp)) // Abstand nach der TopBar

        // Platz für den spezifischen Inhalt der Activity
        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}
