//package com.example.speaktoo.pages
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.example.speaktoo.databinding.GameBinding
//
//import android.media.MediaPlayer
//import android.widget.Toast
//import com.example.speaktoo.api.base.RetrofitClient
//import com.example.speaktoo.models.WordDetailResponse
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class Game : AppCompatActivity() {
//    private lateinit var binding: GameBinding
//    private var mediaPlayer: MediaPlayer? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = GameBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val word = intent.getStringExtra("WORD") ?: return
//
//        fetchWordDetails(word)
//
//        binding.speaker.setOnClickListener {
//            mediaPlayer?.start()
//        }
//
//        binding.arrowGame.setOnClickListener {
//            // Close the current activity
//            finish()
//        }
//
//    }
//
//    private fun fetchWordDetails(word: String) {
//        RetrofitClient.instance.getWordDetails(word).enqueue(object : Callback<WordDetailResponse> {
//            override fun onResponse(call: Call<WordDetailResponse>, response: Response<WordDetailResponse>) {
//                if (response.isSuccessful) {
//                    val wordDetail = response.body()?.data
//                    wordDetail?.let {
//                        // Assuming `it.result` is a list of WordResult objects
//                        val wordsList = it.result.map { it.word }  // Extracting all words from the list
//
//                        // Joining the list into a single string without separators
//                        val allWordsText = wordsList.joinToString(" ")
//
//                        // Setting the concatenated string to your TextView
//                        binding.word.text = allWordsText
//
//                        binding.definition.text = it.result[0].meaning.joinToString("\n\n") { meaning ->
//                            meaning.definitions.joinToString("\n") { definition ->
//                                definition.definition
//                            }
//                        }
//
//                        mediaPlayer = MediaPlayer().apply {
//                            setDataSource(it.audio)
//                            prepare()
//                        }
//                    }
//                } else {
//                    Toast.makeText(this@Game, "Failed to fetch word details", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<WordDetailResponse>, t: Throwable) {
//                Toast.makeText(this@Game, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mediaPlayer?.release()
//        mediaPlayer = null
//    }
//}