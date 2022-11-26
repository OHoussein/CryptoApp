package dev.ohoussein.crypto.presentation.reducer

import app.cash.turbine.test
import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.cryptoapp.common.resource.DataStatus
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.mock

class CryptoListReducerTest : BehaviorSpec({

    given("a reducer") {
        val reducer = CryptoListReducer()

        `when`("a UpdateCryptoList event") {
            val newCryptoList: List<Crypto> = mock()
            val event = CryptoListEvents.UpdateCryptoList(
                cryptoList = newCryptoList,
            )
            reducer.sendEvent(event)

            then("it should set the event's data") {
                reducer.state.test {
                    awaitItem() shouldBe CryptoListState(
                        cryptoList = newCryptoList,
                        status = DataStatus.Success,
                    )
                }
            }
        }

        `when`("a UpdateStatus event") {
            val status: DataStatus = mock()
            val event = CryptoListEvents.UpdateStatus(
                status = status,
            )
            reducer.sendEvent(event)

            then("it should set the event's status") {
                reducer.state.test {
                    awaitItem().status shouldBe status
                }
            }
        }
    }
})
