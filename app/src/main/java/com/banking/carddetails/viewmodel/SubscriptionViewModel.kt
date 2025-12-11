package com.banking.carddetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banking.carddetails.models.Subscription
import com.banking.carddetails.repository.Result
import com.banking.carddetails.repository.SubscriptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SubscriptionUiState(
    val subscriptions: List<Subscription> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val repository: SubscriptionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubscriptionUiState())
    val uiState: StateFlow<SubscriptionUiState> = _uiState.asStateFlow()

    init {
        loadSubscriptions()
    }

    fun loadSubscriptions() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            when (val result = repository.fetchAndAnalyzeSubscriptions()) {
                is Result.Success -> {
                    _uiState.value = SubscriptionUiState(
                        subscriptions = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.exception.message ?: "Unknown error occurred"
                    )
                }
                is Result.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }

    fun getUpcomingSubscriptions(): List<Subscription> {
        return _uiState.value.subscriptions.filter {
            it.category == com.banking.carddetails.models.SubscriptionCategory.UPCOMING
        }
    }

    fun getActiveSubscriptions(): List<Subscription> {
        return _uiState.value.subscriptions.filter {
            it.category == com.banking.carddetails.models.SubscriptionCategory.ACTIVE
        }
    }

    fun getTotalUpcoming(): Double {
        return repository.getTotalUpcoming(_uiState.value.subscriptions)
    }

    fun getTotalActive(): Double {
        return repository.getTotalActive(_uiState.value.subscriptions)
    }
}
