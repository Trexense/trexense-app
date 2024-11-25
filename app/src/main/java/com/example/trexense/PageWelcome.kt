package com.example.trexense

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trexense.databinding.ActivityPageWelcomeBinding
import java.text.Normalizer.Form

class PageWelcome : AppCompatActivity() {
    private lateinit var binding: ActivityPageWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPageWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSignin.setOnClickListener {
            signIn()
        }

        binding.btnSignup.setOnClickListener {
            signUp()
        }

    }

    private fun signUp() {
        TODO("Not yet implemented")
    }

    private fun signIn() {
        val intent = Intent(this@PageWelcome, PageForgotPassword::class.java)
        startActivity(intent)
    }

}