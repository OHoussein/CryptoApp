package dev.ohoussein.crypto.domain.model

val defaultLocale = Locale("USD", "en")

data class Locale(
    val currencyCode: String,
    val languageCode: String,
)
