package com.plezha.mycurrencyconverter.data

import com.plezha.mycurrencyconverter.model.Currency
import com.plezha.mycurrencyconverter.network.CurrencyApi
import io.reactivex.rxjava3.core.Single

class CurrencyRepository(
    private val api: CurrencyApi
) {
    fun getCurrencies(): Single<List<Currency>> {
        return api.getCurrencies()
            .map { it.data.values.toList() }
    }

    fun getConversionRatio(
        currencyFromCode: String,
        currencyToCode: String
    ): Single<Double> {
        return api.getLatestConversionRatio(currencyFromCode, currencyToCode)
            .map { it.data.values.first() }
    }
}
