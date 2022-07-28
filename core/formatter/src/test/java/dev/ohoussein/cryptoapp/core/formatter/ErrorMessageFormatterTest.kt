package dev.ohoussein.cryptoapp.core.formatter

import android.content.Context
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException

private const val ERROR_NETWORK_MESSAGE = "No internet connection"
private const val UNKNOWN_ERROR_MESSAGE = "Server error"
private const val ERROR_UNKNOWN_ERROR_WITH_MESSAGE = "Internal error: $UNKNOWN_ERROR_MESSAGE"
private const val ERROR_UNKNOWN_ERROR = "Internal error"

class ErrorMessageFormatterTest : DescribeSpec({

    describe("an ErrorMessageFormatter") {
        val context = mock<Context>()
        val errorMessageFormatter = ErrorMessageFormatter(context)

        describe("with errors messages") {
            whenever(context.getString(R.string.error_no_internet_connection)).thenReturn(ERROR_NETWORK_MESSAGE)
            whenever(context.getString(R.string.error_unknown_error_with_message, UNKNOWN_ERROR_MESSAGE))
                .thenReturn(ERROR_UNKNOWN_ERROR_WITH_MESSAGE)
            whenever(context.getString(R.string.error_unknown_error)).thenReturn(ERROR_UNKNOWN_ERROR)

            describe("a network error") {
                val errorMessage = errorMessageFormatter.map(IOException())

                it("should format the network error message") {
                    errorMessage shouldBe ERROR_NETWORK_MESSAGE
                }
            }

            describe("an unknown error") {
                val errorMessage = errorMessageFormatter.map(Exception())

                it("should format the unknown error message") {
                    errorMessage shouldBe ERROR_UNKNOWN_ERROR
                }
            }

            describe("an unknown error with message") {
                val errorMessage = errorMessageFormatter.map(Exception(UNKNOWN_ERROR_MESSAGE))

                it("should format the unknown error message") {
                    errorMessage shouldBe ERROR_UNKNOWN_ERROR_WITH_MESSAGE
                }
            }
        }
    }
})
