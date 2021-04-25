package com.example.bcpexchange.presentation.mapper

import com.example.bcpexchange.domain.model.ExchangeRate
import com.example.bcpexchange.presentation.model.ExchangeRateModel

fun ExchangeRate.toPresentation() = ExchangeRateModel(
    buyExchangeRate = buyExchangeRate,
    sellExchangeRate = sellExchangeRate,
    logoUrl = logoUrl,
    currencyCode = currencyCode,
    currencyName = currencyName,
    currencyCountry = currencyCountry
)