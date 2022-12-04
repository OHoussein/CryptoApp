package dev.ohoussein.cryptoapp.data.network.crypto.service.utils

import dev.ohoussein.cryptoapp.data.network.NetworkBuilder
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel

fun mockedHttpClient(response: String) = NetworkBuilder.httpClient(
    MockEngine {
        respond(
            content = ByteReadChannel(response),
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }
)
