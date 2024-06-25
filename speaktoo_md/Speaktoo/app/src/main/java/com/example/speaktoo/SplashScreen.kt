package com.example.speaktoo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.speaktoo.pages.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            // Create an intent to start the MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Start the MainActivity
            finish() // Finish the current activity (SplashScreen)
        }, 3000) // Delay 3 seconds before starting MainActivity
    }
}