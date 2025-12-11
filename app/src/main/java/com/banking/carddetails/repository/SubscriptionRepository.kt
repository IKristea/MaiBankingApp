package com.banking.carddetails.repository

import com.banking.carddetails.models.AnalyzedSubscription
import com.banking.carddetails.models.Subscription
import com.banking.carddetails.models.SubscriptionCategory
import com.banking.carddetails.models.SubscriptionStatus
import com.banking.carddetails.models.Transaction
import com.banking.carddetails.network.TransactionApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

@Singleton
class SubscriptionRepository @Inject constructor(
    private val apiService: TransactionApiService
) {

    suspend fun fetchAndAnalyzeSubscriptions(): Result<List<Subscription>> {
        return try {
            // Step 1: Fetch transactions
            val transactions = apiService.generateTransactions()

            // Step 2: Analyze transactions
            val analyzedSubscriptions = apiService.analyzeTransactions(transactions)

            // Step 3: Convert to Subscription model
            val subscriptions = analyzedSubscriptions.mapIndexed { index, analyzed ->
                convertToSubscription(analyzed, index)
            }

            Result.Success(subscriptions)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    fun getSubscriptionsFlow(): Flow<Result<List<Subscription>>> = flow {
        emit(Result.Loading)
        emit(fetchAndAnalyzeSubscriptions())
    }

    private fun convertToSubscription(
        analyzed: AnalyzedSubscription,
        index: Int
    ): Subscription {
        val nextPaymentDate = calculateNextPaymentDate(analyzed.recurrent_day)
        val isUpcoming = isUpcoming(analyzed.recurrent_day)

        return Subscription(
            id = index.toString(),
            name = analyzed.merchant_info,
            logoUrl = "",
            amount = analyzed.price,
            currency = "MDL",
            frequency = "Monthly",
            nextPaymentDate = formatDate(nextPaymentDate),
            status = if (isUpcoming) SubscriptionStatus.UPCOMING else SubscriptionStatus.ACTIVE,
            category = if (isUpcoming) SubscriptionCategory.UPCOMING else SubscriptionCategory.ACTIVE
        )
    }

    private fun calculateNextPaymentDate(recurrentDay: Int): Date {
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.set(Calendar.DAY_OF_MONTH, recurrentDay)

        // If the payment day has passed this month, move to next month
        if (recurrentDay <= today) {
            calendar.add(Calendar.MONTH, 1)
        }

        return calendar.time
    }

    private fun isUpcoming(recurrentDay: Int): Boolean {
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_MONTH)
        val daysUntilPayment = if (recurrentDay > today) {
            recurrentDay - today
        } else {
            val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            (daysInMonth - today) + recurrentDay
        }

        // Consider upcoming if within 3 days
        return daysUntilPayment <= 3
    }

    private fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("d MMM", Locale.ENGLISH)
        return sdf.format(date)
    }

    fun getTotalUpcoming(subscriptions: List<Subscription>): Double {
        return subscriptions
            .filter { it.category == SubscriptionCategory.UPCOMING }
            .sumOf { it.amount }
    }

    fun getTotalActive(subscriptions: List<Subscription>): Double {
        return subscriptions
            .filter { it.category == SubscriptionCategory.ACTIVE }
            .sumOf { it.amount }
    }
}
