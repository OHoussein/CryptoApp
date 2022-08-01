package dev.ohoussein.core.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.cryptoapp.core.Qualifier
import java.util.Currency
import java.util.Locale
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideLocale(): Locale = Locale.US

    @Provides
    @Named(Qualifier.CURRENCY)
    fun provideCurrency(locale: Locale): String = Currency.getInstance(locale).currencyCode
}
