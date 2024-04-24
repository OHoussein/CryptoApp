package dev.ohoussein.cryptoapp.crypto.presentation.list

sealed class CryptoListEvents {
    data object Refresh : CryptoListEvents()
}
