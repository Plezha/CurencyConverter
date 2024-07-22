package com.plezha.mycurrencyconverter.network

import com.plezha.mycurrencyconverter.model.CurrenciesResponse
import com.plezha.mycurrencyconverter.model.LatestConversionRatioResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CurrencyApi {
    @GET("currencies?apikey=$apiKey")
    fun getCurrencies(): Single<CurrenciesResponse>

    @GET("latest?apikey=$apiKey")
    fun getLatestConversionRatio(
        @Query("base_currency") currencyFromCode: String,
        @Query("currencies") currencyToCode: String,
    ): Single<LatestConversionRatioResponse>
}