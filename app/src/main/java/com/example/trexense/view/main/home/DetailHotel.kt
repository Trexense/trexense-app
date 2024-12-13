package com.example.trexense.view.main.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.trexense.R
import com.example.trexense.data.response.DataHotel
import com.example.trexense.data.utils.Result
import com.example.trexense.databinding.ActivityDetailHotelBinding
import com.example.trexense.view.ViewModelFactory
import com.example.trexense.view.adapter.FacilitiesHotelAdapter
import com.example.trexense.view.createPlan.CreatePlanHotel
import com.example.trexense.view.main.HomeActivity
import com.example.trexense.view.main.home.DetailEvent.Companion
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class DetailHotel : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHotelBinding
    private val viewModel by viewModels<HotelViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idHotel = intent.getStringExtra(DATA_HOTEL)
        Log.d(TAG, "id hotel: $idHotel ")

        binding.icBack.setOnClickListener {
            startActivity(Intent(this@DetailHotel, HomeActivity::class.java))
        }

        if (idHotel != null) {
            getDetailDataHotel(idHotel)
        }

        val hotelIdSave = intent.getStringExtra("HOTEL_ID_SAVE")
        binding.btnCreatePlan.setOnClickListener {
            val intent = Intent(this@DetailHotel, CreatePlanHotel::class.java)
            intent.putExtra("HOTEL_ID", hotelIdSave)
            startActivity(intent)
        }

    }

    private fun getDetailDataHotel(idHotel: String) {
        viewModel.getDetailDataHotel(idHotel).observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> Toast.makeText(
                    this,
                    "${result.error} masuk sini",
                    Toast.LENGTH_LONG
                ).show()
                is Result.Success -> {
                    showLoading(false)
                    val hotel = result.data.data
                    if (hotel != null) {
                        Glide.with(this)
                            .load(hotel.imageUrl)
                            .centerCrop()
                            .apply(
                                RequestOptions.placeholderOf(R.drawable.ic_loading)
                                    .error(R.drawable.ic_error)
                            )
                            .into(binding.ivDetailHotel)

                        Log.d(TAG, "getFacilites : ${result.data.data.filteredFacilites} ")

                        binding.itemOrderListHotel.apply {
                            layoutManager = GridLayoutManager(this@DetailHotel, 2)
                            adapter = FacilitiesHotelAdapter(result.data.data.filteredFacilites)
                        }

                        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
                        binding.tvPrice.text = formatter.format(hotel.cost)
                        val title = hotel.name
                        binding.tvNameHotel.text = title
                        binding.tvPlace.text = hotel.address
                        binding.tvDescription.text = hotel.description
                    } else {
                        showLoading(false)
                        Toast.makeText(this, "tidak dapat mengambil data", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.icLocation.visibility = View.GONE
            binding.linearLocation.visibility = View.GONE
            binding.btnCreatePlan.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.icLocation.visibility = View.VISIBLE
            binding.linearLocation.visibility = View.VISIBLE
            binding.btnCreatePlan.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        val id = intent.getStringExtra(DATA_HOTEL).toString()
        viewModel.getDetailDataHotel(id)
    }

    companion object {
        const val DATA_HOTEL = "list_hotel"
        private const val TAG = "DetailHotel"
    }
}