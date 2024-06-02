package dev.ohoussein.cryptoapp.core.router

import java.awt.Desktop
import java.net.URI

class DesktopRouter : Router {
    override fun openUrl(url: String) {
        runCatching { Desktop.getDesktop().browse(URI.create(url)) }
            .onFailure {
                println("Errr opening url $url: $it")
                it.printStackTrace()
            }
    }
}
