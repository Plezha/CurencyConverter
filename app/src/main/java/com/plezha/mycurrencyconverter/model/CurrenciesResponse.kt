package com.plezha.mycurrencyconverter.model

import com.google.gson.annotations.SerializedName

data class CurrenciesResponse(
    @SerializedName("data")
    val data: Map<String, Currency>
)