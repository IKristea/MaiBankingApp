# Backend Implementation Guide

## Overview

This document describes the backend architecture implemented for the Banking App. The backend fetches transaction data from a remote API, analyzes it to detect recurring subscriptions, and displays them in the UI.

## API Endpoints

### Base URL
```
https://3000-firebase-transactions-analyze-1763127692333.cluster-6aufaxcfanfh2quaz7stglulic.cloudworkstations.dev/
```

### 1. Generate Transactions
- **Endpoint**: `GET /generate-transactions`
- **Response**: List of transactions
```json
[
  {
    "date": "2025-07-03T18:14:47.416278",
    "mcc": "5818",
    "merchant_info": "Amazon Prime",
    "price": 44.43
  },
  {
    "date": "2025-07-03T18:14:47.416278",
    "mcc": "5818",
    "merchant_info": "Netflix",
    "price": 50.75
  }
]
```

### 2. Analyze Transactions
- **Endpoint**: `POST /analyze-transactions`
- **Request Body**: List of transactions from the previous endpoint
- **Response**: List of detected subscriptions
```json
[
  {
    "merchant_info": "Hulu",
    "price": 41.42,
    "recurrent_day": 11
  },
  {
    "merchant_info": "Amazon Prime",
    "price": 27.38,
    "recurrent_day": 8
  }
]
```

## Architecture

### 1. Network Layer (`network/`)

**TransactionApiService.kt**
- Retrofit interface defining API endpoints
- Uses coroutines for async operations

```kotlin
interface TransactionApiService {
    @GET("generate-transactions")
    suspend fun generateTransactions(): List<Transaction>

    @POST("analyze-transactions")
    suspend fun analyzeTransactions(@Body transactions: List<Transaction>): List<AnalyzedSubscription>
}
```

### 2. Data Models (`models/`)

**Transaction.kt**
- `Transaction`: Represents a single transaction from the API
- `AnalyzedSubscription`: Represents a detected recurring subscription

### 3. Dependency Injection (`di/`)

**NetworkModule.kt**
- Provides Retrofit, OkHttpClient, Gson instances
- Configures HTTP logging interceptor
- Manages network timeouts (30 seconds)

### 4. Repository Layer (`repository/`)

**SubscriptionRepository.kt**
- Handles data operations and business logic
- Fetches transactions and analyzes them
- Converts API responses to app's Subscription model
- Calculates next payment dates based on recurrent_day
- Determines if a subscription is "upcoming" (within 3 days)

**Result sealed class**
- `Success<T>`: Contains successful data
- `Error`: Contains exception
- `Loading`: Indicates loading state

### 5. ViewModel Layer (`viewmodel/`)

**SubscriptionViewModel.kt**
- Manages UI state using StateFlow
- Exposes subscription data to UI
- Handles loading and error states
- Provides helper methods:
  - `getUpcomingSubscriptions()`: Filters upcoming renewals
  - `getActiveSubscriptions()`: Filters active subscriptions
  - `getTotalUpcoming()`: Calculates total upcoming amount
  - `getTotalActive()`: Calculates total active amount

### 6. Application Setup

**BankingApplication.kt**
- Application class annotated with `@HiltAndroidApp`
- Initializes Hilt dependency injection

**AndroidManifest.xml**
- Declares internet permissions
- Registers BankingApplication

## Dependencies

Added to `gradle/libs.versions.toml`:

```toml
[versions]
retrofit = "2.9.0"
okhttp = "4.12.0"
gson = "2.10.1"
coroutines = "1.7.3"
lifecycleViewModel = "2.10.0"
hilt = "2.48"
hiltNavigationCompose = "1.0.0"

[libraries]
# Networking
retrofit = { module = "com.squareup.retrofit2:retrofit", version.ref = "retrofit" }
retrofit-gson = { module = "com.squareup.retrofit2:converter-gson", version.ref = "retrofit" }
okhttp = { module = "com.squareup.okhttp3:okhttp", version.ref = "okhttp" }
okhttp-logging = { module = "com.squareup.okhttp3:logging-interceptor", version.ref = "okhttp" }
gson = { module = "com.google.code.gson:gson", version.ref = "gson" }

# Coroutines
kotlinx-coroutines-core = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core", version.ref = "coroutines" }
kotlinx-coroutines-android = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-android", version.ref = "coroutines" }

# ViewModel
androidx-lifecycle-viewmodel-ktx = { module = "androidx.lifecycle:lifecycle-viewmodel-ktx", version.ref = "lifecycleViewModel" }
androidx-lifecycle-viewmodel-compose = { module = "androidx.lifecycle:lifecycle-viewmodel-compose", version.ref = "lifecycleViewModel" }

# Hilt
hilt-android = { module = "com.google.dagger:hilt-android", version.ref = "hilt" }
hilt-compiler = { module = "com.google.dagger:hilt-compiler", version.ref = "hilt" }
hilt-navigation-compose = { module = "androidx.hilt:hilt-navigation-compose", version.ref = "hiltNavigationCompose" }

[plugins]
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
kotlin-kapt = { id = "org.jetbrains.kotlin.kapt", version.ref = "kotlin" }
```

## UI Integration

### SubscriptionsListScreen
- Displays all subscriptions (upcoming and active)
- Shows loading indicator while fetching data
- Shows error message with retry button on failure
- Calculates and displays total amounts

### CardDetailsScreen
- Displays upcoming subscriptions (up to 2)
- Fetches data from ViewModel
- Updates automatically when data changes

## Data Flow

1. **App Launch**: ViewModel is created and automatically calls `loadSubscriptions()`
2. **Fetch Transactions**: Repository calls `generateTransactions()` API
3. **Analyze**: Repository sends transactions to `analyzeTransactions()` API
4. **Transform**: Repository converts `AnalyzedSubscription` to `Subscription` model
   - Calculates next payment date based on `recurrent_day`
   - Determines if subscription is upcoming or active
5. **Update UI**: StateFlow emits new state to UI
6. **Display**: Composables observe state and render subscriptions

## Date Calculation Logic

```kotlin
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
    val daysUntilPayment = calculateDaysUntilPayment(recurrentDay)
    return daysUntilPayment <= 3  // Within 3 days
}
```

## Error Handling

- Network errors are caught and wrapped in `Result.Error`
- UI displays error messages with retry functionality
- HTTP logging helps debug API issues

## Testing the Implementation

1. **Build the project**:
   ```bash
   ./gradlew clean build
   ```

2. **Run the app**:
   ```bash
   ./gradlew installDebug
   ```

3. **Verify API calls**:
   - Check Logcat for HTTP requests/responses
   - Look for OkHttp logs

## Future Enhancements

1. **Caching**: Add Room database for offline support
2. **Pull to refresh**: Allow manual data refresh
3. **Search**: Implement subscription search functionality
4. **Filters**: Add filtering by price, date, merchant
5. **Analytics**: Track subscription spending over time
6. **Notifications**: Alert users before upcoming payments

## Troubleshooting

### No data showing
- Check internet connection
- Verify API endpoint is accessible
- Check Logcat for network errors

### Build errors
- Sync Gradle files
- Clean and rebuild project
- Invalidate caches and restart Android Studio

### Hilt errors
- Ensure `@HiltAndroidApp` is on Application class
- Verify `@AndroidEntryPoint` is on MainActivity
- Check all modules are properly annotated
