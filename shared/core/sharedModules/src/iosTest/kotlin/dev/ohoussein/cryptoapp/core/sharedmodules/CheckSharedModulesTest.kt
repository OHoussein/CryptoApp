package dev.ohoussein.cryptoapp.core.sharedmodules

import kotlin.test.Ignore
import kotlin.test.Test
import org.koin.test.check.checkKoinModules

class CheckSharedModulesTest {

    @Test
    @Ignore
    // TODO: not works because of this error: "Undefined symbols for architecture x86_64: _sqlite3_bind_blob"
    fun test_sharedModules() {
        checkKoinModules(sharedModules)
    }
}
