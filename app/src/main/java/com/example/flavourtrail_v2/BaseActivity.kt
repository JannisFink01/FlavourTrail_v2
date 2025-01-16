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
import com.example.flavourtrail_v2.ui.CustomNavigationBar
import com.example.flavourtrail_v2.ui.theme.FlavourTrail_v2Theme

/**
 * Abstract base class for all activities in the app.
 * This class defines the common layout and structure that each activity will follow.
 */
abstract class BaseActivity : ComponentActivity() {

    /**
     * Called when the activity is created.
     * It sets the content of the activity using a common theme and layout.
     *
     * @param savedInstanceState The saved state of the activity, if any.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlavourTrail_v2Theme {
                BaseLayout { Content() }
            }
        }
    }

    /**
     * Abstract composable function that defines the unique content for each activity.
     * Each activity should provide its specific UI components here.
     */
    @Composable
    abstract fun Content()
}

/**
 * A composable function that defines the common layout structure for all activities.
 * It includes a top bar, the main content area, and a custom navigation bar.
 *
 * @param content The composable content to be displayed in the middle of the screen.
 */
@Composable
fun BaseLayout(content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Place the TopBar at the top of the screen
        TopBar(
            userName = "John Doe", // Example user name
            profileImageRes = R.drawable.profile_user // Example profile image
        )

        // Add some space below the TopBar
        Spacer(modifier = Modifier.height(16.dp))

        // Main content occupies the remaining space in the middle
        Box(
            modifier = Modifier
                .weight(1f) // Take up available space
                .fillMaxSize()
        ) {
            content()
        }

        // Place the custom navigation bar at the bottom of the screen
        CustomNavigationBar(onItemSelected = { selectedItem ->
            // Handle the selection of navigation items
            when (selectedItem) {
                "Home" -> MainActivity()
                "Routes" -> PlanRouteActivity()
                //"Premium" -> navigateToPremium()
            }
        })
    }
}
