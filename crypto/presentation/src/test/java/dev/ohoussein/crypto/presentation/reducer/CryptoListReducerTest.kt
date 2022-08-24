package dev.ohoussein.crypto.presentation.reducer

import app.cash.turbine.test
import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.cryptoapp.common.resource.Resource
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.mock

class CryptoListReducerTest : BehaviorSpec({

    given("a reducer") {
        val reducer = CryptoListReducer()

        `when`("a UpdateCryptoListResource event") {
            val newCryptoList = Resource.success<List<Crypto>>(mock())
            val event = CryptoListEvents.UpdateCryptoListResource(
                cryptoList = newCryptoList,
            )
            reducer.sendEvent(event)

            then("it should set the state from the event's data") {
                reducer.state.test {
                    awaitItem() shouldBe CryptoListState(newCryptoList)
                }
            }
        }
    }
})
