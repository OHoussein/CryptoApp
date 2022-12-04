package dev.ohoussein.cryptoapp.common.navigation

import android.app.Application
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import io.kotest.matchers.shouldBe
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class ExternalRouterTest {

    @Test
    fun openWebUrl_should_opens_web_page() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val externalRouter = ExternalRouter(app)
        val url = "https://url.com"

        externalRouter.openWebUrl(url)

        val intent = shadowOf(app).peekNextStartedActivity()
        intent.action shouldBe Intent.ACTION_VIEW
        intent.data?.toString() shouldBe url
    }
}
