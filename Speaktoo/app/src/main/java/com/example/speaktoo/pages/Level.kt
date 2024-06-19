package com.example.speaktoo.pages

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.speaktoo.adapters.WordsAdapter
import com.example.speaktoo.api.base.RetrofitClient
import com.example.speaktoo.databinding.LevelBinding
import com.example.speaktoo.models.WordsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Level : AppCompatActivity() {
    private lateinit var binding: LevelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val topic = intent.getStringExtra("TOPIC") ?: return

        binding.wordRecycle.layoutManager = LinearLayoutManager(this)

        fetchWords(topic)


        binding.arrowLevel.setOnClickListener {
            // Close the current activity
            finish()
        }
    }

    private fun fetchWords(topic: String) {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", null)

        if (uid != null) {
            val requestBody = mapOf("uid" to uid)

            RetrofitClient.instance.getWordsByDifficulty(topic, requestBody).enqueue(object : Callback<WordsResponse> {
                override fun onResponse(call: Call<WordsResponse>, response: Response<WordsResponse>) {
                    if (response.isSuccessful) {
                        val words = response.body()?.data
                        if (words != null) {
                            binding.wordRecycle.adapter = WordsAdapter(words, this@Level) // Pass context here
                        }
                    } else {
                        Toast.makeText(this@Level, "Failed to fetch words", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<WordsResponse>, t: Throwable) {
                    Toast.makeText(this@Level, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "User ID not found. Please login again.", Toast.LENGTH_SHORT).show()
        }
    }
}