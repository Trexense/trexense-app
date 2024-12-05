package com.example.trexense.view.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trexense.databinding.ActivityLoginBinding
import com.example.trexense.view.main.HomeActivity
import com.example.trexense.view.PageForgotPassword
import com.example.trexense.view.PageWelcome
import com.example.trexense.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivArrowBack.setOnClickListener {
            startActivity(Intent(this@LoginActivity, PageWelcome::class.java))

        }
        binding.clickForgotPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, PageForgotPassword::class.java))
        }

        binding.tvClickSignup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        binding.btnSignin.setOnClickListener {
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        }

    }
}