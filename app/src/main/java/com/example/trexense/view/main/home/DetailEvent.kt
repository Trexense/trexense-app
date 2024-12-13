package com.example.trexense.view.main.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Snackbar
import androidx.core.net.ParseException
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.trexense.R
import com.example.trexense.data.response.DataItem
import com.example.trexense.data.utils.Result
import com.example.trexense.databinding.ActivityDetailEventBinding
import com.example.trexense.view.EventViewModelFactory
import com.example.trexense.view.createPlan.CreatePlanViewModel
import com.example.trexense.view.main.HomeActivity
import com.example.trexense.view.main.plan.PlanFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.text.format

class DetailEvent : AppCompatActivity() {
    private lateinit var binding: ActivityDetailEventBinding

    private val viewModel: CreatePlanViewModel by viewModels {
        EventViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val event =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra<DataItem>(DATA_EVENT)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra(DATA_EVENT)
            }
        binding.icBack.setOnClickListener {
            startActivity(Intent(this@DetailEvent, HomeActivity::class.java))
        }

        getDetailData(event)



    }

    private fun getDetailData(event: DataItem?) {
        binding.progressBar.visibility = View.VISIBLE
        try {
            if (event != null ) {
                binding.progressBar.visibility = View.GONE
                Glide.with(this)
                    .load(event.imageUrl)
                    .centerCrop()
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(binding.ivDetailEvent)

                val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
                binding.tvPrice.text = formatter.format(event.price)
                Log.d(TAG, "GET DETAIL EVENT : $event ")
                val title = event.title
                binding.tvNameEvent.text = title
                binding.tvAddress.text = event.location
                binding.tvSocialMedia.text = event.targetUrl
                binding.tvDescription.text = event.description
                val eventStartDate = event.startDate
                val eventEndDate = event.validUntil
                if (eventStartDate != null && eventEndDate != null) {
                    val startDate = formatDate(eventStartDate!!)
                    val endDate = formatDateValidUntil(eventEndDate)
                    binding.tvStartDate.text = startDate
                    binding.tvEndDate.text = endDate

                    val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val date = inputFormat.parse(eventStartDate)
                    val inputStartDate = outputFormat.format(date!!)

                    binding.btnSavePlan.setOnClickListener {
                        AlertDialog.Builder(this).apply {
                            setMessage("Do you want to add this ${event.title} to the plan ?")
                            setPositiveButton("Add Plan") {_, _ ->
                                createPlan(title, inputStartDate.toString(), eventEndDate.toString())
                            }
                            setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()
                            }
                            create()
                            show()
                        }
                    }
                }



            } else {
                Log.d(TAG, "Data Detail event NULL ")
            }
        }catch (e: Exception) {
            binding.progressBar.visibility = View.GONE
            Log.d(TAG, "getDetailData: ${e.message}", e)
        }

    }

    private fun createPlan(title: String?, eventStartDate: String?, eventEndDate: String?) {
        if (title !=null && eventStartDate != null && eventEndDate != null) {
            viewModel.createPlan(title, eventStartDate.toString(), eventEndDate.toString())
            observeViewModel()
        } else {
            Log.d("DetailEvent", "Gagal Create Plan: ")
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) {isLoading ->
            showLoading(isLoading)
        }

        viewModel.createPlanResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> Toast.makeText(this, result.error, Toast.LENGTH_LONG).show()
                is Result.Success -> {
                    Toast.makeText(this, "Your event has been add in plan", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun formatDate(schedule: String): String{
                val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val outputFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id", "ID"))

        return try {
            val date = inputFormat.parse(schedule)
            outputFormat.format(date!!)
        } catch (e: ParseException) {
            // Tangani exception, misalnya tampilkan pesan error atau kembalikan string kosong
            Log.e("DetailEvent", "Error parsing date: ${e.message}")
            ""
        }

    }

    private fun formatDateValidUntil(schedule: String): String{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEEE, d MMMM yyyy",  Locale("id", "ID"))

        return try {
            val date = inputFormat.parse(schedule)
            outputFormat.format(date!!)
        } catch (e: ParseException) {
            // Tangani exception, misalnya tampilkan pesan error atau kembalikan string kosong
            Log.e("DetailEvent", "Error parsing date: ${e.message}")
            ""
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val DATA_EVENT = "data_event"
        private const val TAG = "DetailEvent"
    }
}