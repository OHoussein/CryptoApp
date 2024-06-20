package dev.ohoussein.cryptoapp.core.formatter

import kotlinx.datetime.TimeZone
import org.koin.core.qualifier.named
import org.koin.dsl.module

val formatModule = module {
    factory {
        getPercentFormatter(get(named("languageCode")))
    }
    factory {
        getPriceFormatter(get(named("languageCode")))
    }
    factory {
        TimeZone.currentSystemDefault()
    }
}
