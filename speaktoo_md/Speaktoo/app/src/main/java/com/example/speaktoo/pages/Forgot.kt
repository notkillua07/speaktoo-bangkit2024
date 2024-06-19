package com.example.speaktoo.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.speaktoo.databinding.ForgotBinding

class Forgot : AppCompatActivity() {
    private lateinit var binding: ForgotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ForgotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.arrowForgot.setOnClickListener {
            // Close the current activity
            finish()
        }

    }
}
