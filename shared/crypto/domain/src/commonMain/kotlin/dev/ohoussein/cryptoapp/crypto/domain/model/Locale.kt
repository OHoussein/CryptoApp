package dev.ohoussein.cryptoapp.crypto.domain.model

val defaultLocale = Locale("USD", "en")

data class Locale(
    val currencyCode: String,
    val languageCode: String,
)
