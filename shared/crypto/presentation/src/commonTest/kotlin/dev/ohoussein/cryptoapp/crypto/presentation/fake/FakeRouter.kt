package dev.ohoussein.cryptoapp.crypto.presentation.fake

import dev.ohoussein.cryptoapp.core.router.Router

class FakeRouter : Router {
    val openedUrls = mutableListOf<String>()
    override fun openUrl(url: String) {
        openedUrls.add(url)
    }
}
