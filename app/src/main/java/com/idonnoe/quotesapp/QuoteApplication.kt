package com.idonnoe.quotesapp

import android.app.Application
import com.idonnoe.quotesapp.db.QuoteDatabase
import com.idonnoe.quotesapp.repository.QuotesRepository
import com.idonnoe.quotesapp.retrofit.QuotesAPI
import com.idonnoe.quotesapp.retrofit.RetrofitHelper

class QuoteApplication: Application() {

    lateinit var quotesRepository: QuotesRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val quotesAPI = RetrofitHelper.getInstance().create(QuotesAPI::class.java)
        val database = QuoteDatabase.getDatabase(applicationContext)
        quotesRepository = QuotesRepository(quotesAPI, database, applicationContext)
    }
}