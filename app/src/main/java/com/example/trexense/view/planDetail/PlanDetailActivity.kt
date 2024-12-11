package com.example.trexense.view.planDetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trexense.R
import com.example.trexense.data.utils.Result
import com.example.trexense.databinding.ActivityPlanDetailBinding
import com.example.trexense.view.EventViewModelFactory
import com.example.trexense.view.main.plan.PlanViewModel

class PlanDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlanDetailBinding
    private val viewModel: PlanDetailViewModel by viewModels {
        EventViewModelFactory.getInstance(this)
    }
    private lateinit var planDayAdapter: PlanDayAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPlanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.back.setOnClickListener {
            finish()
        }

        setupRecyclerView()
        val id = intent.getStringExtra("PLAN_ID")
        viewModel.getDetailPlan(id.toString())
        observeViewModel()
    }

    private fun setupRecyclerView() {
        planDayAdapter = PlanDayAdapter { item ->
            // Handle item click here, for example:
            Toast.makeText(this, "Clicked on: Day ${item.day}", Toast.LENGTH_SHORT).show()
        }
        binding.rcDay.apply {
            layoutManager = LinearLayoutManager(this@PlanDetailActivity)
            adapter = planDayAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        viewModel.planDetailResult.observe(this) { result ->
            when(result) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> Toast.makeText(this@PlanDetailActivity, result.error, Toast.LENGTH_SHORT).show()
                is Result.Success -> {
                    val planDetails = result.data.data.planDetails
                    planDayAdapter.submitList(planDetails)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}