package com.banking.carddetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.banking.carddetails.navigation.AppNavHost
import com.banking.carddetails.ui.theme.BankingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display (removes system bars)
        enableEdgeToEdge()

        // Make status bar transparent
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            BankingTheme {
                Surface(
                    modifier = Modifier,
                    color = Color(0xFFF7F8FA)
                ) {
                    val nav = rememberNavController()
                    AppNavHost(nav)
                }
            }
        }
    }
}
