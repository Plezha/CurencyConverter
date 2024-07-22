package com.plezha.mycurrencyconverter.ui

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.plezha.mycurrencyconverter.CurrencyConverterApplication
import com.plezha.mycurrencyconverter.data.CurrencyRepository
import com.plezha.mycurrencyconverter.model.Currency
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivityViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {
    var currencies = listOf<Currency>()
    var amount: Double? = null
    val convertedAmount = MutableLiveData<Double>()

    var chosenCurrencyFromIndex: Int? = null
        set(value) {
            if (value != null && value >= currencies.size) throw IndexOutOfBoundsException()
            field = value
        }
    var chosenCurrencyToIndex: Int? = null
        set(value) {
            if (value != null && value >= currencies.size) throw IndexOutOfBoundsException()
            field = value
        }

    private var convertedCurrencySubscription: Disposable? = null

    init {
        observeCurrencies()
    }

    @SuppressLint("CheckResult")
    private fun observeCurrencies() {
        currencyRepository.getCurrencies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    currencies = it
                },
                {
                    println(it)
                }
            )
    }

    fun getConvertedCurrencyAmount() {
        if (chosenCurrencyFromIndex == null || chosenCurrencyFromIndex == null || amount == null) {
            throw IllegalStateException(
                "This function should only be called after successful input"
            )
        }
        convertedCurrencySubscription = currencyRepository.getConversionRatio(
            currencies[chosenCurrencyFromIndex!!].code,
            currencies[chosenCurrencyToIndex!!].code
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it * amount!! }
            .subscribe(
                {
                    convertedAmount.value = it
                },
                {
                    println(it)
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        convertedCurrencySubscription?.dispose()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val currencyRepository = (this[APPLICATION_KEY] as CurrencyConverterApplication).currencyRepository
                MainActivityViewModel(currencyRepository)
            }
        }
    }
}