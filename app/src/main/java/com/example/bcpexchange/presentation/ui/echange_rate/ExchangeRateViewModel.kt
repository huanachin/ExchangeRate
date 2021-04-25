package com.example.bcpexchange.presentation.ui.echange_rate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bcpexchange.core.functional.Either
import com.example.bcpexchange.core.functional.LiveEvent
import com.example.bcpexchange.domain.interactor.GetInitialExchangeRate
import com.example.bcpexchange.presentation.mapper.toPresentation
import com.example.bcpexchange.presentation.model.ExchangeRateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExchangeRateViewModel @Inject constructor(private val getInitialExchangeRateUseCase: GetInitialExchangeRate) :
    ViewModel() {

    private val _initialExchangeRateLiveData = LiveEvent<GetInitialExchangeRateState>()
    val initialExchangeRateState: LiveData<GetInitialExchangeRateState> get() = _initialExchangeRateLiveData

    fun fetchInitialExchangeRate() {
        viewModelScope.launch {
            _initialExchangeRateLiveData.value = GetInitialExchangeRateState.Loading
            when (val result = withContext(Dispatchers.IO) { getInitialExchangeRateUseCase() }) {
                is Either.Error -> {
                    _initialExchangeRateLiveData.value =
                        GetInitialExchangeRateState.Error(result.value)
                }
                is Either.Success -> {
                    val exchangeRate = result.value.toPresentation()
                    _initialExchangeRateLiveData.value =
                        GetInitialExchangeRateState.Success(exchangeRate)
                }
            }
        }
    }
}