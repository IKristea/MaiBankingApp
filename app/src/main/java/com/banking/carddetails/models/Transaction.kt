package com.banking.carddetails.models

data class Transaction(
    val date: String,
    val mcc: String,
    val merchant_info: String,
    val price: Double
)

data class AnalyzedSubscription(
    val merchant_info: String,
    val price: Double,
    val recurrent_day: Int
)
