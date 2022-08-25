package dev.ohoussein.crypto.presentation

object NavPath {
    const val HOME = "home"

    object CryptoDetailsPath {
        const val ARG_CRYPTO_ID = "id"

        const val PATH = "crypto/{$ARG_CRYPTO_ID}"

        fun path(id: String) = "crypto/$id"
    }
}
