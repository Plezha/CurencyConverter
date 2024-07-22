package com.plezha.mycurrencyconverter

import android.app.Application
import com.plezha.mycurrencyconverter.data.CurrencyRepository
import com.plezha.mycurrencyconverter.network.CurrencyApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class CurrencyConverterApplication : Application() {
    lateinit var currencyRepository: CurrencyRepository

    override fun onCreate() {
        super.onCreate()

        val api = Retrofit.Builder()
            .baseUrl("https://api.freecurrencyapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(CurrencyApi::class.java)

        currencyRepository = CurrencyRepository(api)
    }
}