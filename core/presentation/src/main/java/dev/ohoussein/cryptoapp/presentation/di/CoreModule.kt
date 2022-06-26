package dev.ohoussein.cryptoapp.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.text.NumberFormat
import java.util.*
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    fun provideLocale(): Locale = Locale.US

    @Provides
    @Named(DIConstants.Qualifier.PRICE_FORMATTER)
    fun providePriceFormatter(locale: Locale): NumberFormat =
            NumberFormat.getCurrencyInstance(locale)

    @Provides
    @Named(DIConstants.Qualifier.PERCENT_FORMATTER)
    fun providePercentFormatter(): NumberFormat = NumberFormat.getPercentInstance().apply {
        minimumFractionDigits = 2
    }
}
