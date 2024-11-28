package com.example.todoapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TodoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = DarkGreenPrimary,
            secondary = DarkGreenSecondary,
            background = DarkGreenBackground,
            surface = Color.Black,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White,
            onError = Color.White
        )
    } else {
        lightColorScheme(
            primary = GreenPrimary,
            secondary = GreenSecondary,
            background = GreenBackground,
            surface = Color.White,
            error = GreenError,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.Black,
            onSurface = Color.Black,
            onError = Color.White
        )
    }

    // Apply the color scheme in MaterialTheme
    MaterialTheme(
        colorScheme = colorScheme,  // Pass the color scheme here
        content = content  // Apply the theme to the content
    )
}


