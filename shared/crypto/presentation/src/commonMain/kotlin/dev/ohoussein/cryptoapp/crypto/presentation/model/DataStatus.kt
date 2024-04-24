package dev.ohoussein.cryptoapp.crypto.presentation.model

import androidx.compose.runtime.Stable

@Stable
sealed interface DataStatus {
    data object Success : DataStatus
    data class Error(val message: String) : DataStatus
    data object Loading : DataStatus
}
