package com.example.bcpexchange.domain.interactor

import com.example.bcpexchange.core.exception.Failure
import com.example.bcpexchange.core.functional.Either
import com.example.bcpexchange.data.mapper.toDomain
import com.example.bcpexchange.domain.model.ExchangeRate
import com.example.bcpexchange.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class GetExchangeRateUseCase @Inject constructor(private val exchangeRateRepository: ExchangeRateRepository) {

    suspend operator fun invoke(): Either<Failure, List<ExchangeRate>> {
        return exchangeRateRepository.getExchangeRates()
            .mapSuccess { exchangeRates -> exchangeRates.map { it.toDomain() } }
    }
}