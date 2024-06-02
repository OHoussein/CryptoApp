package dev.ohoussein.cryptoapp.crypto.presentation.list

sealed interface CryptoListEvents {
    data object Refresh : CryptoListEvents
}
