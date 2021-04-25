package com.example.bcpexchange.data.repository

import com.example.bcpexchange.core.exception.Failure
import com.example.bcpexchange.core.exception.Failure.NetworkFailure
import com.example.bcpexchange.core.exception.Failure.ServerFailure
import com.example.bcpexchange.core.functional.Either
import com.example.bcpexchange.core.functional.Either.Error
import com.example.bcpexchange.core.functional.Either.Success
import com.example.bcpexchange.core.platform.NetworkHandler
import com.example.bcpexchange.data.api.ExchangeRateApi
import com.example.bcpexchange.data.model.ExchangeRateEntity
import com.example.bcpexchange.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
    private val exchangeRateApi: ExchangeRateApi,
    private val networkHandler: NetworkHandler
) : ExchangeRateRepository {

    override suspend fun getExchangeRates(): Either<Failure, List<ExchangeRateEntity>> {
        return if (networkHandler.isNetworkAvailable()) {
            return try {
                val response = exchangeRateApi.fetchExchangeRates()
                return Success(response)
            } catch (throwable: Throwable) {
                Error(ServerFailure)
            }
        } else {
            Error(NetworkFailure)
        }
    }
}