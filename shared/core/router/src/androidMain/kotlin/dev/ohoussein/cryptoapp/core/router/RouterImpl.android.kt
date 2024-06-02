package dev.ohoussein.cryptoapp.core.router

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri

class RouterImpl(private val context: Context) : Router {
    override fun openUrl(url: String) {
        runCatching {
            context.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                    addFlags(FLAG_ACTIVITY_NEW_TASK)
                }
            )
        }.onFailure {
            println("Errr opening url $url: $it")
            it.printStackTrace()
        }
    }
}
