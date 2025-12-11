# Backend Integration Guide

## Overview
This document outlines the data models and API endpoints needed for backend integration of the subscription management feature.

## Data Models

### Subscription
```kotlin
data class Subscription(
    val id: String,
    val name: String,
    val logoUrl: String,
    val amount: Double,
    val currency: String = "MDL",
    val frequency: String = "Monthly",
    val nextPaymentDate: String,
    val status: SubscriptionStatus = SubscriptionStatus.ACTIVE,
    val category: SubscriptionCategory = SubscriptionCategory.ACTIVE
)
```

### SubscriptionDetails
```kotlin
data class SubscriptionDetails(
    val subscription: Subscription,
    val source: String,
    val rrn: String,
    val appc: String,
    val additionalInfo: String,
    val paymentHistory: List<PaymentHistoryItem>
)
```

### CancellationFeedback
```kotlin
data class CancellationFeedback(
    val reasonId: String,
    val additionalComments: String = "",
    val likelihoodToReturn: LikelihoodLevel? = null
)
```

## Required API Endpoints

### 1. Get Subscriptions List
**Endpoint:** `GET /api/subscriptions`

**Response:**
```json
{
  "subscriptions": [
    {
      "id": "string",
      "name": "string",
      "logoUrl": "string",
      "amount": 0.0,
      "currency": "MDL",
      "frequency": "Monthly",
      "nextPaymentDate": "string",
      "status": "ACTIVE",
      "category": "ACTIVE"
    }
  ],
  "totalUpcoming": 0.0,
  "totalActive": 0.0
}
```

### 2. Get Subscription Details
**Endpoint:** `GET /api/subscriptions/{id}`

**Response:**
```json
{
  "subscription": { /* Subscription object */ },
  "source": "string",
  "rrn": "string",
  "appc": "string",
  "additionalInfo": "string",
  "paymentHistory": [
    {
      "date": "string",
      "amount": 0.0,
      "currency": "MDL",
      "status": "Completed"
    }
  ]
}
```

### 3. Get Cancellation Reasons
**Endpoint:** `GET /api/subscriptions/cancellation-reasons`

**Response:**
```json
{
  "reasons": [
    {
      "id": "string",
      "text": "string"
    }
  ]
}
```

### 4. Cancel Subscription
**Endpoint:** `POST /api/subscriptions/{id}/cancel`

**Request Body:**
```json
{
  "reasonId": "string",
  "additionalComments": "string",
  "likelihoodToReturn": "NEUTRAL"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Subscription cancelled successfully",
  "endDate": "string"
}
```

### 5. Manage Subscription (Get Service Info)
**Endpoint:** `GET /api/subscriptions/{id}/manage-info`

**Response:**
```json
{
  "serviceName": "string",
  "features": [
    {
      "number": "string",
      "description": "string"
    }
  ]
}
```

## Implementation Notes

### Current Mock Data Location
- **Models:** `/app/src/main/java/com/banking/carddetails/models/Subscription.kt`
- **Mock Data:** `/app/src/main/java/com/banking/carddetails/data/MockSubscriptionData.kt`

### Steps to Integrate Backend

1. **Create Repository Layer**
   - Create `SubscriptionRepository` interface
   - Implement with Retrofit/OkHttp for API calls
   - Replace `MockSubscriptionData` calls with repository methods

2. **Add ViewModel Layer**
   - Create `SubscriptionsViewModel` for list screen
   - Create `SubscriptionDetailsViewModel` for details screen
   - Handle loading states, errors, and data caching

3. **Update Screens**
   - Replace `remember { MockSubscriptionData.* }` with ViewModel state
   - Add loading states (use `LoadingScreen` component)
   - Add error handling UI

4. **Authentication**
   - Add user authentication token to API requests
   - Handle token refresh
   - Handle unauthorized errors

5. **Image Loading**
   - Implement image loading library (Coil/Glide)
   - Replace color-based logos with actual service logos from `logoUrl`

### Example ViewModel Structure

```kotlin
class SubscriptionsViewModel(
    private val repository: SubscriptionRepository
) : ViewModel() {

    private val _subscriptions = MutableStateFlow<List<Subscription>>(emptyList())
    val subscriptions: StateFlow<List<Subscription>> = _subscriptions

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadSubscriptions() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _subscriptions.value = repository.getSubscriptions()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
```

## Navigation Flow

1. **Main Card Details** → Click "Monthly Subscriptions"
2. **Subscriptions List** → Click subscription item
3. **Subscription Details** → Click "Manage subscription"
4. **Manage Subscription** → Click "Continue to cancel"
5. **Confirm Cancel** → Click "Yes, cancel"
6. **Cancel Reason** → Select reason and click "Submit" OR "Skip"

## TODO Comments in Code

Search for `// TODO:` in the codebase for specific integration points:
- `Navigation.kt:54` - Implement add subscription flow
- `Navigation.kt:122` - Send cancellation data to backend

## Testing

Before backend integration:
- All screens work with mock data
- Navigation flow is complete
- UI matches design specifications

After backend integration:
- Test with real API endpoints
- Test error scenarios (network errors, timeouts)
- Test loading states
- Test empty states (no subscriptions)
