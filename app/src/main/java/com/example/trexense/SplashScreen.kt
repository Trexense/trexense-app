package com.example.trexense

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trexense.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var splashBinding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        splashBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        // Simulasi proses loading
        Handler().postDelayed({
            // Pindah ke halaman welcome
            val intent = Intent(this@SplashScreen, PageWelcome::class.java)
            startActivity(intent)
            finish()
        }, 2000) // selama 2 detik
    }
}