package com.example.trexense.view.createActivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trexense.R
import com.example.trexense.data.utils.Result
import com.example.trexense.databinding.ActivityCreateAktivitasBinding
import com.example.trexense.view.EventViewModelFactory

class CreateAktivitasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAktivitasBinding
    private val viewModel: CreateActivityViewModel by viewModels {
        EventViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreateAktivitasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.saveButton.setOnClickListener {
            val dayId = intent.getStringExtra("DAY_ID") ?: ""
            val name = binding.activityName.text.toString().trim()
            val location = binding.activityLocation.text.toString().trim()
            val cost = binding.activityCost.text.toString().trim()
            val description = binding.activityDescription.text.toString().trim()

            if (name.isEmpty() || location.isEmpty() || cost.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            } else {
                // Pass the data to the ViewModel
                viewModel.addActivity(dayId, name, location, cost, description)
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        viewModel.addResult.observe(this) { result ->
            when(result) {
                is Result.Error -> Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}