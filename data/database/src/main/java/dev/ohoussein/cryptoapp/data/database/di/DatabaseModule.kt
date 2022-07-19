package dev.ohoussein.cryptoapp.data.database.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.crypto.data.database.CryptoDAO
import dev.ohoussein.cryptoapp.data.database.CryptoDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CryptoDatabase =
        CryptoDatabase.build(context)

    @Provides
    @Singleton
    fun provideCryptoDAO(db: CryptoDatabase): CryptoDAO = db.cryptoDAO()
}
