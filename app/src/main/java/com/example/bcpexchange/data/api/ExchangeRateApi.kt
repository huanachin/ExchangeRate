package com.example.bcpexchange.data.api

import com.example.bcpexchange.data.model.ExchangeRateEntity
import com.example.bcpexchange.data.network.ApiUrl.GET_CURRENCY_EXCHANGES_URL
import retrofit2.http.GET

interface ExchangeRateApi {
    @GET(GET_CURRENCY_EXCHANGES_URL)
    suspend fun fetchExchangeRates(): List<ExchangeRateEntity>
}