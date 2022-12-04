package dev.ohoussein.cryptoapp.common.formatter

import android.content.Context
import dev.ohoussein.cryptoapp.core.common.R
import java.io.IOException

class ErrorMessageFormatter constructor(private val context: Context) {

    operator fun invoke(exception: Throwable): String {
        return when (exception) {
            is IOException -> context.getString(R.string.error_no_internet_connection)
            else -> {
                if (exception.message != null)
                    context.getString(R.string.error_unknown_error_with_message, exception.message)
                else
                    context.getString(R.string.error_unknown_error)
            }
        }
    }
}
