package com.example.bcpexchange.domain.interactor

import com.example.bcpexchange.core.exception.Failure
import com.example.bcpexchange.core.functional.Either
import com.example.bcpexchange.data.mapper.toDomain
import com.example.bcpexchange.domain.model.ExchangeRate
import com.example.bcpexchange.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class GetInitialExchangeRate @Inject constructor(private val exchangeRateRepository: ExchangeRateRepository) {

    suspend operator fun invoke(currencyCode: String = "USD"): Either<Failure, ExchangeRate> {
        when (val result = exchangeRateRepository.getExchangeRates()) {
            is Either.Error -> {
                return Either.Error(result.value)
            }
            is Either.Success -> {
                val exchangeRate = result.value.find { it.currencyCode == currencyCode }
                    ?: return Either.Error(Failure.UnexpectedFailure)
                return Either.Success(exchangeRate.toDomain())
            }
        }
         
    }
}