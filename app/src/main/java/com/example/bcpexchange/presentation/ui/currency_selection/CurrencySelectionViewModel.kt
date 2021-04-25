package com.example.bcpexchange.presentation.ui.currency_selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bcpexchange.core.functional.Either
import com.example.bcpexchange.domain.interactor.GetExchangeRateUseCase
import com.example.bcpexchange.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrencySelectionViewModel @Inject constructor(
    private val getExchangeRateUseCase: GetExchangeRateUseCase
) : ViewModel() {

    private val _exchangeRatesLiveData = MutableLiveData<GetExchangeRatesState>()
    val exchangeRatesLiveData: LiveData<GetExchangeRatesState> get() = _exchangeRatesLiveData

    fun fetchExchangeRates() {
        viewModelScope.launch {
            _exchangeRatesLiveData.value = GetExchangeRatesState.Loading
            when (val result = withContext(Dispatchers.IO) { getExchangeRateUseCase() }) {
                is Either.Error -> {
                    _exchangeRatesLiveData.value = GetExchangeRatesState.Error(result.value)
                }
                is Either.Success -> {
                    _exchangeRatesLiveData.value =
                        GetExchangeRatesState.Success(result.value.map { it.toPresentation() })
                }
            }
        }
    }
}