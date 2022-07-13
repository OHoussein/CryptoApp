package dev.ohoussein.core.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.cryptoapp.presentation.mapper.PercentMapper
import dev.ohoussein.cryptoapp.presentation.mapper.PriceMapper
import java.util.Locale

@Module
@InstallIn(SingletonComponent::class)
object FormatModule {

    @Provides
    fun provideLocale(): Locale = Locale.US

    @Provides
    fun providePriceFormatter(locale: Locale): PriceMapper = PriceMapper(locale)

    @Provides
    fun providePercentFormatter(locale: Locale): PercentMapper = PercentMapper(locale)
}
