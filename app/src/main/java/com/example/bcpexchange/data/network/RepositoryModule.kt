package com.example.bcpexchange.data.network

import com.example.bcpexchange.data.repository.ExchangeRateRepositoryImpl
import com.example.bcpexchange.domain.repository.ExchangeRateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesExchangeRateRepository(exchangeRateRepositoryImpl: ExchangeRateRepositoryImpl): ExchangeRateRepository
}