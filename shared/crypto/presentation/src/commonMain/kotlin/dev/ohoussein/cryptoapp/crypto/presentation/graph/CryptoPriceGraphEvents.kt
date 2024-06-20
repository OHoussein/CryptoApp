package dev.ohoussein.cryptoapp.crypto.presentation.graph

import dev.ohoussein.cryptoapp.crypto.presentation.model.GraphInterval

sealed interface CryptoPriceGraphEvents {
    data class SelectInterval(val interval: GraphInterval) : CryptoPriceGraphEvents
}
