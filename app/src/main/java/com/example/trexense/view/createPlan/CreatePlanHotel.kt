package com.example.trexense.view.createPlan

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trexense.data.response.DataPlans
import com.example.trexense.data.utils.Result
import com.example.trexense.databinding.ActivityCreatePlanHotelBinding
import com.example.trexense.view.EventViewModelFactory
import com.example.trexense.view.adapter.PlanHotelAdapter
import com.example.trexense.view.main.plan.PlanViewModel

class CreatePlanHotel : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePlanHotelBinding
    private val viewModel: PlanViewModel by viewModels {
        EventViewModelFactory.getInstance(this)
    }
    private var selectedPlanId: String? = null // Untuk menyimpan ID plan yang dipilih

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePlanHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val hotelId = intent.getStringExtra("HOTEL_ID")
        viewModel.getPlans()
        observeViewModel()

        binding.backButton.setOnClickListener { finish() }
        binding.saveButton.setOnClickListener {
            if (selectedPlanId != null) {
                viewModel.saveHotel(selectedPlanId!!, hotelId.toString())
                Toast.makeText(this, "Hotel linked to plan: $selectedPlanId", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please select a plan first.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        viewModel.plansResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> Toast.makeText(this, result.error, Toast.LENGTH_LONG).show()
                is Result.Success -> {
                    setupRecyclerView(result.data.data) // Panggil RecyclerView setup
                    showLoading(false)
                }
            }
        }
        viewModel.saveResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> Toast.makeText(this, result.error, Toast.LENGTH_LONG).show()
                is Result.Success -> {
                    Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun setupRecyclerView(plans: List<DataPlans>) {
        val adapter = com.example.trexense.view.main.plan.PlanHotelAdapter(plans) { selectedPlan ->
            selectedPlanId = selectedPlan.id
            binding.inputNameSelectEdit.setText("${selectedPlan.name}")
        }
        binding.rvPlan.apply {
            layoutManager = LinearLayoutManager(this@CreatePlanHotel)
            this.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
