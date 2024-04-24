package dev.ohoussein.cryptoapp.crypto.presentation.list

import dev.ohoussein.cryptoapp.crypto.presentation.model.Crypto
import dev.ohoussein.cryptoapp.crypto.presentation.model.DataStatus

data class CryptoListState(
    val cryptoList: List<Crypto>? = null,
    val status: DataStatus = DataStatus.Loading,
)
