package com.example.trexense.view.createPlan

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trexense.R
import com.example.trexense.data.utils.Result
import com.example.trexense.databinding.ActivityCreatePlanBinding
import com.example.trexense.databinding.ActivityCreatePlanHotelBinding
import com.example.trexense.view.EventViewModelFactory
import com.example.trexense.view.adapter.PlanHotelAdapter
import com.example.trexense.view.main.plan.PlanViewModel
import kotlinx.coroutines.launch

class CreatePlanHotel : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePlanHotelBinding
    private val viewModel: PlanViewModel by viewModels {
        EventViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePlanHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener((binding.main)){ v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.getPlans()
        observeViewModel()



    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.isLoading.observe(this@CreatePlanHotel) { isLoading ->
                showLoading(isLoading)
            }
            viewModel.plansResult.observe(this@CreatePlanHotel) { result ->
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Error -> Toast.makeText(this@CreatePlanHotel, result.error, Toast.LENGTH_LONG)
                        .show()
                    is Result.Success -> {
                        val plansNames = result.data.data.map { it.name }
//                        val adapterPlanHotel = PlanHotelAdapter(plansNames)
//                        binding.rvPlans.apply {
//                            layoutManager = LinearLayoutManager(this@CreatePlanHotel)
//                            adapter = adapterPlanHotel
//                        }

                        // Buat ArrayAdapter
                        val adapter = ArrayAdapter(this@CreatePlanHotel, R.layout.item_plans_hotel, plansNames)
                        val autoCompleteTextView = binding.inputNameSelectEdit
                        autoCompleteTextView.setAdapter(adapter)
                    }
                }
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}