package com.example.trexense.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trexense.R
import com.example.trexense.databinding.ActivityPageForgotPasswordBinding

class PageForgotPassword : AppCompatActivity() {
    private lateinit var binding: ActivityPageForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPageForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnForgotPassword.setOnClickListener {
            val intent = Intent(this@PageForgotPassword, PageResetPassword::class.java)
            startActivity(intent)
        }

    }
}