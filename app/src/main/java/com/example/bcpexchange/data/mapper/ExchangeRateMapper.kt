package com.example.bcpexchange.data.mapper

import com.example.bcpexchange.data.model.ExchangeRateEntity
import com.example.bcpexchange.domain.model.ExchangeRate

fun ExchangeRateEntity.toDomain() = ExchangeRate(
    buyExchangeRate = buyExchangeRate,
    sellExchangeRate = sellExchangeRate,
    logoUrl = logoUrl,
    currencyCode = currencyCode,
    currencyName = currencyName,
    currencyCountry = currencyCountry
)