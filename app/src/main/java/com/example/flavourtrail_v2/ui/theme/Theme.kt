package com.example.flavourtrail_v2.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Predefined dark color scheme for the app's theme.
 */
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

/**
 * Predefined light color scheme for the app's theme.
 */
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

/**
 * Composable function to apply the app's theme, dynamically switching between
 * dark and light themes based on system settings or parameters.
 *
 * @param darkTheme Boolean flag to enforce dark theme. Defaults to the system setting.
 * @param dynamicColor Boolean flag to enable dynamic colors (available on Android 12+).
 * @param content The content to apply the theme to.
 */
@Composable
fun FlavourTrail_v2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Use dynamic color schemes on Android 12+ if enabled
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        // Fallback to predefined dark or light color schemes
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Apply the Material 3 theme with the selected color scheme and typography
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}