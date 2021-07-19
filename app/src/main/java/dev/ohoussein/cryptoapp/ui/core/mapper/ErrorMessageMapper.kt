package dev.ohoussein.cryptoapp.ui.core.mapper

import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import dev.ohoussein.cryptoapp.R
import java.io.IOException
import javax.inject.Inject

class ErrorMessageMapper @Inject constructor(@ActivityContext private val context: Context) {

    fun map(exception: Throwable?): String {
        return when (exception) {
            is IOException -> context.getString(R.string.error_no_internet_connection)
            else -> {
                if (exception?.message != null)
                    context.getString(R.string.error_unknown_error_with_message, exception.message)
                else
                    context.getString(R.string.error_unknown_error)
            }
        }
    }
}
