package dev.ohoussein.cryptoapp.crypto.presentation.details

sealed class CryptoDetailsEvents {
    data object Refresh : CryptoDetailsEvents()
}
