package com.example.bcpexchange.domain.repository

import com.example.bcpexchange.core.exception.Failure
import com.example.bcpexchange.core.functional.Either
import com.example.bcpexchange.data.model.ExchangeRateEntity

interface ExchangeRateRepository {
    suspend fun getExchangeRates(): Either<Failure, List<ExchangeRateEntity>>
}