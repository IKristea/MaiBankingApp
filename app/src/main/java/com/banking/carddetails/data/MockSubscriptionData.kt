package com.banking.carddetails.data

import com.banking.carddetails.models.*

object MockSubscriptionData {

    val cancellationReasons = listOf(
        CancellationReason("1", "I had issues with payment and billing"),
        CancellationReason("2", "I cannot find the music or podcasts that I like"),
        CancellationReason("3", "I have found another audio or music service I like better"),
        CancellationReason("4", "I am switching to a different Spotify subscription"),
        CancellationReason("5", "I have had technical problems related to using my Spotify subscription"),
        CancellationReason("6", "I had issues with plan verification"),
        CancellationReason("7", "I cannot afford a Spotify subscription"),
        CancellationReason("8", "I don't use my Spotify subscription enough"),
        CancellationReason("9", "Other (please specify below)")
    )

    val subscriptions = listOf(
        Subscription(
            id = "1",
            name = "Amazon",
            logoUrl = "",
            amount = 70.0,
            nextPaymentDate = "15 Dec",
            category = SubscriptionCategory.UPCOMING,
            status = SubscriptionStatus.UPCOMING
        ),
        Subscription(
            id = "2",
            name = "Capcut",
            logoUrl = "",
            amount = 100.0,
            nextPaymentDate = "25 Dec",
            category = SubscriptionCategory.UPCOMING,
            status = SubscriptionStatus.UPCOMING
        ),
        Subscription(
            id = "3",
            name = "Youtube",
            logoUrl = "",
            amount = 100.0,
            nextPaymentDate = "5 Dec",
            category = SubscriptionCategory.ACTIVE,
            status = SubscriptionStatus.ACTIVE
        ),
        Subscription(
            id = "4",
            name = "Netflix",
            logoUrl = "",
            amount = 129.0,
            nextPaymentDate = "5 Dec",
            category = SubscriptionCategory.ACTIVE,
            status = SubscriptionStatus.ACTIVE
        ),
        Subscription(
            id = "5",
            name = "Spotify",
            logoUrl = "",
            amount = 129.0,
            nextPaymentDate = "5 Dec",
            category = SubscriptionCategory.ACTIVE,
            status = SubscriptionStatus.ACTIVE
        ),
        Subscription(
            id = "6",
            name = "Amazon",
            logoUrl = "",
            amount = 70.0,
            nextPaymentDate = "10 Dec",
            category = SubscriptionCategory.ACTIVE,
            status = SubscriptionStatus.ACTIVE
        ),
        Subscription(
            id = "7",
            name = "Capcut",
            logoUrl = "",
            amount = 100.0,
            nextPaymentDate = "5 Dec",
            category = SubscriptionCategory.ACTIVE,
            status = SubscriptionStatus.ACTIVE
        ),
        Subscription(
            id = "8",
            name = "iCloud",
            logoUrl = "",
            amount = 260.0,
            nextPaymentDate = "11 Dec",
            category = SubscriptionCategory.ACTIVE,
            status = SubscriptionStatus.ACTIVE
        ),
        Subscription(
            id = "9",
            name = "Canva Pro",
            logoUrl = "",
            amount = 179.0,
            nextPaymentDate = "1 Dec",
            category = SubscriptionCategory.ACTIVE,
            status = SubscriptionStatus.ACTIVE
        )
    )

    fun getSubscriptionDetails(subscriptionId: String): SubscriptionDetails? {
        val subscription = subscriptions.find { it.id == subscriptionId } ?: return null

        return SubscriptionDetails(
            subscription = subscription,
            source = "gama universal •4345",
            rrn = "21834728493232",
            appc = "846253",
            additionalInfo = "Chișinău",
            nextBillingDate = "05.01.2026",
            paymentHistory = listOf(
                PaymentHistoryItem(
                    date = "05.12.2025",
                    amount = subscription.amount
                ),
                PaymentHistoryItem(
                    date = "05.11.2025",
                    amount = subscription.amount
                ),
                PaymentHistoryItem(
                    date = "05.10.2025",
                    amount = subscription.amount
                )
            )
        )
    }

    fun getTotalUpcoming(): Double {
        return subscriptions
            .filter { it.category == SubscriptionCategory.UPCOMING }
            .sumOf { it.amount }
    }

    fun getTotalActive(): Double {
        return subscriptions
            .filter { it.category == SubscriptionCategory.ACTIVE }
            .sumOf { it.amount }
    }
}
