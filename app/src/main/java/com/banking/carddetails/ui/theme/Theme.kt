// Theme.kt (updated primary color to teal to match screenshot)
package com.banking.carddetails.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00BCD4), // Teal
    secondary = Color(0xFFFF6B6B),
    tertiary = Color(0xFF4CAF50)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00BCD4), // Teal
    secondary = Color(0xFFFF6B6B),
    tertiary = Color(0xFF4CAF50)
)

@Composable
fun BankingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}