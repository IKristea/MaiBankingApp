package com.banking.carddetails.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.banking.carddetails.*

object Routes {
    const val HOME = "home"
    const val CARD = "card"
    const val SMART = "smart_subscriptions"
    const val SUBSCRIPTIONS_LIST = "subscriptions_list"
    const val SUBSCRIPTION_DETAILS = "subscription_details/{subscriptionId}"
    const val SUBSCRIPTION_PAYMENT_HISTORY = "subscription_payment_history/{subscriptionId}"
    const val MANAGE_SUBSCRIPTION = "manage_subscription/{subscriptionId}"
    const val CONFIRM_CANCEL = "confirm_cancel/{subscriptionId}"
    const val CANCEL_REASON = "cancel_reason/{subscriptionId}"
    const val LOADING = "loading"

    fun subscriptionDetails(subscriptionId: String) = "subscription_details/$subscriptionId"
    fun subscriptionPaymentHistory(subscriptionId: String) = "subscription_payment_history/$subscriptionId"
    fun manageSubscription(subscriptionId: String) = "manage_subscription/$subscriptionId"
    fun confirmCancel(subscriptionId: String) = "confirm_cancel/$subscriptionId"
    fun cancelReason(subscriptionId: String) = "cancel_reason/$subscriptionId"
}

@Composable
fun AppNavHost(nav: NavHostController) {
    NavHost(
        navController = nav,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onCardClick = { nav.navigate(Routes.CARD) },
                onNotificationClick = { /* TODO: Implement notifications */ },
                onUpcomingPaymentsClick = { nav.navigate(Routes.SUBSCRIPTIONS_LIST) }
            )
        }

        composable(Routes.CARD) {
            CardDetailsScreen(
                onBack = { nav.popBackStack() },
                onEdit = { /* TODO: Implement edit */ },
                onSubscriptions = { nav.navigate(Routes.SUBSCRIPTIONS_LIST) }
            )
        }

        composable(Routes.SMART) {
            SmartSubscriptionsScreen(
                onBack = { nav.popBackStack() }
            )
        }

        composable(Routes.SUBSCRIPTIONS_LIST) {
            SubscriptionsListScreen(
                onBack = { nav.popBackStack() },
                onSubscriptionClick = { subscriptionId ->
                    nav.navigate(Routes.subscriptionDetails(subscriptionId))
                },
                onAddSubscription = {
                    // TODO: Implement add subscription flow
                }
            )
        }

        composable(
            route = Routes.SUBSCRIPTION_DETAILS,
            arguments = listOf(navArgument("subscriptionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val subscriptionId = backStackEntry.arguments?.getString("subscriptionId") ?: ""
            SubscriptionDetailsScreen(
                subscriptionId = subscriptionId,
                onBack = { nav.popBackStack() },
                onPaymentHistoryClick = { id ->
                    nav.navigate(Routes.subscriptionPaymentHistory(id))
                },
                onManageSubscription = { id ->
                    nav.navigate(Routes.manageSubscription(id))
                }
            )
        }

        composable(
            route = Routes.SUBSCRIPTION_PAYMENT_HISTORY,
            arguments = listOf(navArgument("subscriptionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val subscriptionId = backStackEntry.arguments?.getString("subscriptionId") ?: ""
            SubscriptionPaymentHistoryScreen(
                subscriptionId = subscriptionId,
                onBack = { nav.popBackStack() },
            )
        }

        composable(
            route = Routes.MANAGE_SUBSCRIPTION,
            arguments = listOf(navArgument("subscriptionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val subscriptionId = backStackEntry.arguments?.getString("subscriptionId") ?: ""
            ManageSubscriptionScreen(
                subscriptionId = subscriptionId,
                onBack = { nav.popBackStack() },
                onContinueToCancel = { id ->
                    nav.navigate(Routes.confirmCancel(id))
                },
                onBackToAccount = {
                    // Navigate back to subscription details
                    nav.popBackStack()
                }
            )
        }

        composable(
            route = Routes.CONFIRM_CANCEL,
            arguments = listOf(navArgument("subscriptionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val subscriptionId = backStackEntry.arguments?.getString("subscriptionId") ?: ""
            ConfirmCancelScreen(
                subscriptionId = subscriptionId,
                onBack = { nav.popBackStack() },
                onYesCancel = { id ->
                    nav.navigate(Routes.cancelReason(id))
                },
                onBackToAccount = {
                    // Navigate back to subscription details
                    nav.popBackStack(Routes.subscriptionDetails(subscriptionId), inclusive = false)
                }
            )
        }

        composable(
            route = Routes.CANCEL_REASON,
            arguments = listOf(navArgument("subscriptionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val subscriptionId = backStackEntry.arguments?.getString("subscriptionId") ?: ""
            CancelReasonScreen(
                subscriptionId = subscriptionId,
                onBack = { nav.popBackStack() },
                onSkip = {
                    // Navigate back to main screen or subscription list
                    nav.popBackStack(Routes.SUBSCRIPTIONS_LIST, inclusive = false)
                },
                onSubmit = { reasonId, comments, likelihood ->
                    // TODO: Send data to backend when implemented
                    // For now, navigate back to subscription list
                    nav.popBackStack(Routes.SUBSCRIPTIONS_LIST, inclusive = false)
                }
            )
        }

        composable(Routes.LOADING) {
            LoadingScreen(
                title = "Loading",
                onBack = { nav.popBackStack() }
            )
        }
    }
}
