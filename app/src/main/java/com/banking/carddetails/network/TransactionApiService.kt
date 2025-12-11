package com.banking.carddetails.network

import com.banking.carddetails.models.AnalyzedSubscription
import com.banking.carddetails.models.Transaction
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransactionApiService {

    @GET("generate-transactions")
    suspend fun generateTransactions(): List<Transaction>

    @POST("analyze-transactions")
    suspend fun analyzeTransactions(
        @Body transactions: List<Transaction>
    ): List<AnalyzedSubscription>
}
