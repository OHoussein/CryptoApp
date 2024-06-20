package dev.ohoussein.cryptoapp.crypto.presentation.details

import cryptoapp.shared.crypto.presentation.generated.resources.*
import dev.ohoussein.cryptoapp.crypto.presentation.model.CryptoDetails
import dev.ohoussein.cryptoapp.crypto.presentation.model.DataStatus

data class CryptoDetailsState(
    val cryptoDetails: CryptoDetails? = null,
    val status: DataStatus = DataStatus.Loading,
)
