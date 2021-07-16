package dev.ohoussein.cryptoapp.di

object DIConstants {
    object Qualifier {
        const val HTTP_NETWORK_INTERCEPTOR = "QUALIFIER_HTTP_NETWORK_INTERCEPTOR"
        const val HTTP_INTERCEPTOR = "QUALIFIER_HTTP_INTERCEPTOR"

        const val PRICE_FORMATTER = "PRICE_FORMATTER"
        const val PERCENT_FORMATTER = "PERCENT_FORMATTER"
    }
}
