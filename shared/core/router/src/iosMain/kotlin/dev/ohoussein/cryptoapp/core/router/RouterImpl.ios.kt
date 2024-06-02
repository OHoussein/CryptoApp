package dev.ohoussein.cryptoapp.core.router

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

class RouterImpl : Router {
    override fun openUrl(url: String) {
        runCatching {
            UIApplication.sharedApplication.openURL(
                url = NSURL(string = url, encodingInvalidCharacters = true),
                options = emptyMap<Any?, Any>(),
                completionHandler = null
            )
        }.onFailure {
            println("Errr opening url $url: $it")
            it.printStackTrace()
        }
    }
}
