package com.example.trexense.view.main.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trexense.R
import com.example.trexense.data.utils.Result
import com.example.trexense.databinding.ActivitySearchBinding
import com.example.trexense.view.EventViewModelFactory
import com.example.trexense.view.createPlan.CreatePlanViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels {
        EventViewModelFactory.getInstance(this)
    }
    private lateinit var searchAdapter: SearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        searchAdapter = SearchAdapter { selectedItem ->
            Toast.makeText(this, selectedItem.name, Toast.LENGTH_SHORT).show()
        }
        with(binding.rcSearch) {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
        }
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { v, actionId, event ->
                    val hotelName = v.text.toString()
                    searchBar.setText(hotelName)
                    viewModel.searchHotel(hotelName)
                    true
                }
        }

        binding.back.setOnClickListener { finish() }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        viewModel.searchResult.observe(this) { result ->
            when(result) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> Toast.makeText(this@SearchActivity, result.error, Toast.LENGTH_SHORT).show()
                is Result.Success -> {
                    val hotel = result.data.data
                    searchAdapter.submitList(hotel)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}