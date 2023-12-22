package com.idonnoe.quotesapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.idonnoe.quotesapp.models.Quote

@Dao
interface QuoteDao {

    @Insert
    fun addQuotes(quotes: List<Quote>)

    @Query("SELECT * FROM quote")
    fun getQuotes(): List<Quote>

    @Query("DELETE FROM quote")
    fun deleteQuotes()
}