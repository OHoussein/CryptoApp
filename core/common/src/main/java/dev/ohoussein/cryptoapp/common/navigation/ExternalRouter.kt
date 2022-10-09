package dev.ohoussein.cryptoapp.common.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

data class ExternalRouter @Inject constructor(@ApplicationContext private val context: Context) {

    fun openWebUrl(url: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            context.startActivity(this)
        }
    }
}
