package com.idonnoe.quotesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idonnoe.quotesapp.models.Quote
import com.idonnoe.quotesapp.repository.QuotesRepository
import com.idonnoe.quotesapp.repository.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: QuotesRepository): ViewModel() {

    val quotesLiveData: LiveData<Response<List<Quote>>>
        get() = repository.quotesLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getQuotesFromDB()
        }
    }

    fun fetchQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getQuotes(1)
        }
    }

    fun deleteQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteQuotes()
        }
    }
}