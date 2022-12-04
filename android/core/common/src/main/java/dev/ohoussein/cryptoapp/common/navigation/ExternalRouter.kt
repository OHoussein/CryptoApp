package dev.ohoussein.cryptoapp.common.navigation

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri

class ExternalRouter constructor(private val context: Context) {

    fun openWebUrl(url: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }
}
