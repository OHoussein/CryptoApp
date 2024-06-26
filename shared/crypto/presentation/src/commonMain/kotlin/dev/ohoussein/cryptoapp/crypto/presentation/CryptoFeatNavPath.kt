package dev.ohoussein.cryptoapp.crypto.presentation

object CryptoFeatNavPath {
    const val HOME = "home"

    object CryptoDetailsPath {
        const val ARG_CRYPTO_ID = "id"

        const val PATH = "crypto/{$ARG_CRYPTO_ID}"

        fun path(id: String) = "crypto/$id"
    }
}
