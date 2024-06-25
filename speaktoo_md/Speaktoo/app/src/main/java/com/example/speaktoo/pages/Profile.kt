package com.example.speaktoo.pages

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.speaktoo.databinding.ProfileBinding
import java.io.File

class Profile : AppCompatActivity() {
    private lateinit var binding: ProfileBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.arrowProfile.setOnClickListener {
            // Close the current activity
            finish()
        }
        binding.btnChangePicture.setOnClickListener{startGallery()}

    }
    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            if (uri != null) {
                currentImageUri = uri
            } else {
                Log.d("Photo Picker", "No media selected")
            }
        }
    }
}
