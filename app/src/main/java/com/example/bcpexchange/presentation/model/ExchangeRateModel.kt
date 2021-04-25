package com.example.bcpexchange.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExchangeRateModel(
    val buyExchangeRate: Double,
    val sellExchangeRate: Double,
    val logoUrl: String,
    val currencyCode:String,
    val currencyName:String,
    val currencyCountry:String
):Parcelable