package com.example.bcpexchange.presentation.ui.echange_rate

import com.example.bcpexchange.core.exception.Failure
import com.example.bcpexchange.presentation.model.ExchangeRateModel

sealed class GetInitialExchangeRateState {
    object Loading : GetInitialExchangeRateState()
    class Error(val failure: Failure) : GetInitialExchangeRateState()
    class Success(val exchangeRate: ExchangeRateModel) : GetInitialExchangeRateState()
}