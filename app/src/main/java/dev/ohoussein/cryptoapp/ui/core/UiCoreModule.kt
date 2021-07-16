package dev.ohoussein.cryptoapp.ui.core

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.cryptoapp.di.DIConstants.Qualifier.PERCENT_FORMATTER
import dev.ohoussein.cryptoapp.di.DIConstants.Qualifier.PRICE_FORMATTER
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object UiCoreModule {

    @Provides
    @Named(PRICE_FORMATTER)
    fun providePriceFormatter(): NumberFormat = NumberFormat.getCurrencyInstance()

    @Provides
    @Named(PERCENT_FORMATTER)
    fun providePercentFormatter(): NumberFormat = NumberFormat.getPercentInstance().apply {
        minimumFractionDigits = 2
    }
}
