package dev.ohoussein.core.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.cryptoapp.core.formatter.PercentFormatter
import dev.ohoussein.cryptoapp.core.formatter.PriceFormatter
import java.util.Locale

@Module
@InstallIn(SingletonComponent::class)
object FormatModule {

    @Provides
    fun provideLocale(): Locale = Locale.US

    @Provides
    fun providePriceFormatter(locale: Locale): PriceFormatter = PriceFormatter(locale)

    @Provides
    fun providePercentFormatter(locale: Locale): PercentFormatter = PercentFormatter(locale)
}
