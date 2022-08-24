package dev.ohoussein.crypto.presentation.reducer

import app.cash.turbine.test
import dev.ohoussein.crypto.presentation.model.CryptoDetails
import dev.ohoussein.cryptoapp.common.resource.Resource
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.mock

class CryptoDetailsReducerTest : BehaviorSpec({

    given("a reducer") {
        val reducer = CryptoDetailsReducer()

        `when`("a UpdateCryptoDetailsResource event") {
            val cryptoDetailsResource = Resource.success<CryptoDetails>(mock())
            val event = CryptoDetailsEvents.UpdateCryptoDetailsResource(
                crypto = cryptoDetailsResource,
            )
            reducer.sendEvent(event)

            then("it should set the state from the event's data") {
                reducer.state.test {
                    awaitItem() shouldBe CryptoDetailsState(cryptoDetailsResource)
                }
            }
        }
    }
})
