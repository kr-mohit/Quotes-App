package com.idonnoe.quotesapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.idonnoe.quotesapp.databinding.ActivityMainBinding
import com.idonnoe.quotesapp.repository.Response
import com.idonnoe.quotesapp.viewmodels.MainViewModel
import com.idonnoe.quotesapp.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val repository = (application as QuoteApplication).quotesRepository
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        setListeners()
        setObservers()
    }

    private fun setListeners() {

        binding.btnFetchQuotes.setOnClickListener {
            mainViewModel.fetchQuotes()
        }

        binding.btnDeleteQuotes.setOnClickListener {
            mainViewModel.deleteQuotes()
        }
    }

    private fun setObservers() {

        mainViewModel.quotesLiveData.observe(this) {

            when(it) {
                is Response.Loading -> {
                    binding.tvFetchState.text = getString(R.string.fetching_data)
                }
                is Response.Success -> {
                    binding.tvFetchState.text = getString(R.string.data_fetched_successfully)
                    it.data?.let { list ->

                        val adapter = QuoteAdapter()
                        adapter.submitList(list)
                        binding.rvQuoteList.adapter = adapter
                        binding.rvQuoteList.layoutManager = LinearLayoutManager(this)
                        binding.tvCurrentQuotesCount.text = getString(R.string.current_quotes, list.size.toString())
                    }
                }
                is Response.Error -> {
                    binding.tvFetchState.text = getString(R.string.an_error_occurred)
                    Toast.makeText(this, "Error: ${it.error}", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}