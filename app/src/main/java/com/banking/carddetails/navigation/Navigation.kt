package com.banking.carddetails.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.banking.carddetails.CardDetailsScreen
import com.banking.carddetails.SmartSubscriptionsScreen

object Routes {
    const val CARD = "card"
    const val SMART = "smart_subscriptions"
}

@Composable
fun AppNavHost(nav: NavHostController) {
    NavHost(
        navController = nav,
        startDestination = Routes.CARD
    ) {
        composable(Routes.CARD) {
            CardDetailsScreen(
                onBack = { nav.popBackStack() },
                onSmartSubs = { nav.navigate(Routes.SMART) }
            )
        }

        composable(Routes.SMART) {
            SmartSubscriptionsScreen(
                onBack = { nav.popBackStack() }
            )
        }
    }
}
