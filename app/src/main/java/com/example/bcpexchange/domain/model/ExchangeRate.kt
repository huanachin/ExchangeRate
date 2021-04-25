package com.example.bcpexchange.domain.model

data class ExchangeRate(
    val buyExchangeRate: Double,
    val sellExchangeRate: Double,
    val logoUrl: String,
    val currencyCode:String,
    val currencyName:String,
    val currencyCountry:String
)