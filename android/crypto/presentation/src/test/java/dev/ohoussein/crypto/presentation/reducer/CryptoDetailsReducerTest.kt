package dev.ohoussein.crypto.presentation.reducer

import app.cash.turbine.test
import dev.ohoussein.crypto.presentation.model.CryptoDetails
import dev.ohoussein.cryptoapp.common.resource.DataStatus
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk

class CryptoDetailsReducerTest : BehaviorSpec({

    given("a reducer") {
        val reducer = CryptoDetailsReducer()

        `when`("an UpdateCryptoDetails event") {
            val cryptoDetails: CryptoDetails = mockk()
            val event = CryptoDetailsEvents.UpdateCryptoDetails(
                crypto = cryptoDetails,
            )
            reducer.sendEvent(event)

            then("it should set the state from the event's data") {
                reducer.state.test {
                    awaitItem() shouldBe CryptoDetailsState(
                        cryptoDetails = cryptoDetails,
                        status = DataStatus.Success,
                    )
                }
            }
        }

        `when`("a UpdateStatus event") {
            val status: DataStatus = DataStatus.Success
            val event = CryptoDetailsEvents.UpdateStatus(
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
