package com.example.bcpexchange.data.model

data class ExchangeRateEntity(
    val buyExchangeRate: Double,
    val sellExchangeRate: Double,
    val logoUrl: String,
    val currencyCode:String,
    val currencyName:String,
    val currencyCountry:String
)