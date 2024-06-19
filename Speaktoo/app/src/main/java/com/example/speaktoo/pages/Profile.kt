package com.example.speaktoo.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.speaktoo.databinding.ProfileBinding

class Profile : AppCompatActivity() {
    private lateinit var binding: ProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.arrowProfile.setOnClickListener {
            // Close the current activity
            finish()
        }

    }


}
