package com.example.trexense.view.main.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.trexense.R
import com.example.trexense.data.response.DataItem
import com.example.trexense.databinding.ActivityDetailEventBinding
import com.example.trexense.view.main.HomeActivity
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class DetailEvent : AppCompatActivity() {
    private lateinit var binding: ActivityDetailEventBinding

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
                binding.tvNameEvent.text = event.title
                val date = formatDate(event.startDate)
                binding.tvSchedule.text = date
                binding.tvAddress.text = event.location
                binding.tvSocialMedia.text = event.targetUrl
                binding.tvDescription.text = event.description
            } else {
                Log.d(TAG, "Data Detail event NULL ")
            }
        }catch (e: Exception) {
            binding.progressBar.visibility = View.GONE
            Log.d(TAG, "getDetailData: ${e.message}", e)
        }

    }

    private fun formatDate(schedule: String?): String{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = schedule?.let { inputFormat.parse(it) }
        val outputFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id"))
        return outputFormat.format(date!!)

    }

    companion object {
        const val DATA_EVENT = "data_event"
        private const val TAG = "DetailEvent"
    }
}