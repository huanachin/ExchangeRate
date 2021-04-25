package com.example.bcpexchange.presentation.ui.currency_selection

import com.example.bcpexchange.core.exception.Failure
import com.example.bcpexchange.presentation.model.ExchangeRateModel

sealed class GetExchangeRatesState {
    object Loading : GetExchangeRatesState()
    class Error(val failure: Failure) : GetExchangeRatesState()
    class Success(val exchangeRates: List<ExchangeRateModel>) : GetExchangeRatesState()
}