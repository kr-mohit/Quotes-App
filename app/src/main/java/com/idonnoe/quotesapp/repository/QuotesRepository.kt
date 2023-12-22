package com.idonnoe.quotesapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.idonnoe.quotesapp.db.QuoteDatabase
import com.idonnoe.quotesapp.models.Quote
import com.idonnoe.quotesapp.retrofit.QuotesAPI
import com.idonnoe.quotesapp.utils.NetworkUtils

class QuotesRepository(
    private val quotesAPI: QuotesAPI,
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
) {

    private val _quotesLiveData = MutableLiveData<Response<List<Quote>>>()
    val quotesLiveData:  LiveData<Response<List<Quote>>>
        get() = _quotesLiveData

    suspend fun getQuotes(page: Int) {

        if (NetworkUtils.isOnline(applicationContext)) {

            try {
                _quotesLiveData.postValue(Response.Loading())
                val result = quotesAPI.getQuotes(page)

                if (result.body() != null) {
                    quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
                    getQuotesFromDB()
                } else {
                    _quotesLiveData.postValue(Response.Error("API Error!"))
                }
            } catch (e: Exception) {
                _quotesLiveData.postValue(Response.Error(e.message.toString()))
            }

        } else {
            getQuotesFromDB()
        }
    }

    fun deleteQuotes() {
        quoteDatabase.quoteDao().deleteQuotes()
        getQuotesFromDB()
    }

    fun getQuotesFromDB() {

        try {
            val quotes = quoteDatabase.quoteDao().getQuotes()
            _quotesLiveData.postValue(Response.Success(quotes))
        } catch (e: Exception) {
            _quotesLiveData.postValue(Response.Error(e.message.toString()))
        }
    }
}