package com.example.trexense.view.main.home

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.trexense.databinding.ActivityDetailHotelBinding

class DetailHotel : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHotelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}