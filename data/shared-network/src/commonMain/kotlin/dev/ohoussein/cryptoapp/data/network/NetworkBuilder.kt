package dev.ohoussein.cryptoapp.data.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkBuilder {
    fun httpClient(engine: HttpClientEngine? = null): HttpClient {
        val config: HttpClientConfig<*>.() -> Unit = {
            install(Logging) {
                level = LogLevel.INFO
                logger = object : Logger {
                    override fun log(message: String) {
                        println("---------------HTTP---------------")
                        println(message)
                        println("----------------------------------")
                    }
                }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
        return engine?.let { HttpClient(it, config) } ?: HttpClient(config)
    }
}
