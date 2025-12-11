package com.banking.carddetails.models

data class Subscription(
    val id: String,
    val name: String,
    val logoUrl: String, // For future backend integration
    val amount: Double,
    val currency: String = "MDL",
    val frequency: String = "Monthly",
    val nextPaymentDate: String,
    val status: SubscriptionStatus = SubscriptionStatus.ACTIVE,
    val category: SubscriptionCategory = SubscriptionCategory.ACTIVE
)

enum class SubscriptionStatus {
    ACTIVE,
    UPCOMING,
    CANCELLED,
    PAUSED
}

enum class SubscriptionCategory {
    UPCOMING,
    ACTIVE
}

data class SubscriptionDetails(
    val subscription: Subscription,
    val source: String, // e.g., "gama universal •4345"
    val rrn: String,
    val appc: String,
    val additionalInfo: String,
    val paymentHistory: List<PaymentHistoryItem>
)

data class PaymentHistoryItem(
    val date: String,
    val amount: Double,
    val currency: String = "MDL",
    val status: String = "Completed"
)

data class CancellationReason(
    val id: String,
    val text: String
)

data class CancellationFeedback(
    val reasonId: String,
    val additionalComments: String = "",
    val likelihoodToReturn: LikelihoodLevel? = null
)

enum class LikelihoodLevel {
    EXTREMELY_LIKELY,
    LIKELY,
    NEUTRAL,
    UNLIKELY,
    EXTREMELY_UNLIKELY
}
