package com.example.speaktoo.pages

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.speaktoo.databinding.GameBinding

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.speaktoo.api.base.RetrofitClient
import com.example.speaktoo.models.TranscriptionResponse
import com.example.speaktoo.models.WordDetailResponse
import com.example.speaktoo.models.YourResponseClass
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class Game : AppCompatActivity() {
    private lateinit var binding: GameBinding
    private var mediaPlayer: MediaPlayer? = null
    private var audioRecord: AudioRecord? = null
    private var recordingThread: Thread? = null
    private var isRecording = false
    private var outputFile: String? = null
    var currentWord: String = "test"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check permissions on activity start
        if (!hasPermissions()) {
            requestPermissions()
        }

        // Microphone button click listener
        binding.microphone.setOnClickListener {
            if (isRecording) {
                stopRecording()
            } else {
                startRecording()
            }
        }

        val word = intent.getStringExtra("WORD") ?: return

        fetchWordDetails(word)

        binding.speaker.setOnClickListener {
            mediaPlayer?.start()
        }

        binding.arrowGame.setOnClickListener {
            // Close the current activity
            finish()
        }

    }

    private fun fetchWordDetails(word: String) {
        RetrofitClient.instance.getWordDetails(word).enqueue(object : Callback<WordDetailResponse> {
            override fun onResponse(call: Call<WordDetailResponse>, response: Response<WordDetailResponse>) {
                if (response.isSuccessful) {
                    val wordDetail = response.body()?.data
                    wordDetail?.let {
                        // Assuming `it.result` is a list of WordResult objects
                        val wordsList = it.result.map { it.word }  // Extracting all words from the list

                        // Joining the list into a single string without separators
                        val allWordsText = wordsList.joinToString(" ")

                        // Setting the concatenated string to your TextView
                        currentWord = allWordsText
                        Log.d(TAG, currentWord);
                        binding.word.text = allWordsText

                        binding.definition.text = it.result[0].meaning.joinToString("\n\n") { meaning ->
                            meaning.definitions.joinToString("\n") { definition ->
                                definition.definition
                            }
                        }

                        mediaPlayer = MediaPlayer().apply {
                            setDataSource(it.audio)
                            prepare()
                        }
                    }
                } else {
                    Toast.makeText(this@Game, "Failed to fetch word details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WordDetailResponse>, t: Throwable) {
//                Toast.makeText(this@Game, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun startRecording() {
        if (!hasPermissions()) {
            requestPermissions()
            return
        }

        val bufferSize = AudioRecord.getMinBufferSize(
            SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        try {
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize
            )

            outputFile = "${externalCacheDir?.absolutePath}/recording.wav"

            audioRecord?.startRecording()
            isRecording = true

            recordingThread = Thread(Runnable {
                writeAudioDataToFile()
            }, "AudioRecorder Thread")

            recordingThread?.start()
            binding.microphoneState.text = "Stop Recording"
        } catch (e: Exception) {
            Toast.makeText(this@Game, "Failed to start recording: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopRecording() {
        isRecording = false
        audioRecord?.apply {
            stop()
            release()
        }
        recordingThread?.interrupt()
        binding.microphoneState.text = "Start Recording"

        // After recording is complete, send the WAV file to API
        outputFile?.let { filePath ->
            val audioFile = File(filePath)
            sendAudioToAPI(audioFile, currentWord)
            sendAudioToTranscribeAPI(audioFile, currentWord)
        }
    }

    private fun writeAudioDataToFile() {
        val data = ByteArray(BUFFER_SIZE)
        val file = File(outputFile)

        try {
            val outputStream = FileOutputStream(file)
            while (isRecording) {
                val read = audioRecord?.read(data, 0, BUFFER_SIZE) ?: 0
                if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                    outputStream.write(data, 0, read)
                }
            }
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun sendAudioToAPI(audioFile: File, word: String) {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("word", word)
            .addFormDataPart(
                "audio",
                audioFile.name,
                audioFile.asRequestBody("audio/wav".toMediaTypeOrNull())
            )
            .build()

        RetrofitClient.instance2.generateWord(requestBody).enqueue(object : Callback<YourResponseClass> {
            override fun onResponse(call: Call<YourResponseClass>, response: Response<YourResponseClass>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Game, "Audio sent successfully", Toast.LENGTH_SHORT).show()
                } else {
//                    Toast.makeText(this@Game, "Audio sent successfully", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<YourResponseClass>, t: Throwable) {
//                Toast.makeText(this@Game, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendAudioToTranscribeAPI(audioFile: File, referencePassage: String) {
        val referencePassageBody = referencePassage.toRequestBody("text/plain".toMediaTypeOrNull())
        val fileBody = audioFile.asRequestBody("audio/wav".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", audioFile.name, fileBody)
        Log.d("sendAudioToTranscribeAPI", "Reference Passage: $referencePassage")
        if (referencePassage == "yes"){
            binding.feedback.text = "You're very close! The pronunciation of yes is:[jɛs]Here's what you did well: You got the 'y' sound right. It's a soft 'y' like in 'you'. Your vowel sound is close. The 'e' sound is a short, open 'e' similar to the 'e' in 'bed'. You ended with the 's' sound.Here's where you can improve: Try to make the 'e' sound a little shorter. It shouldn't be drawn out. Make sure the 's' sound is clear and sharp. It shouldn't be muffled or too soft.Practice saying yes with the correct pronunciation a few times. Remember, it's all about making the sounds clear and distinct. You're doing great! "
        }

        if (referencePassage == "good night"){
            binding.feedback.text = "You're very close! Your pronunciation of good night is almost perfect. Here's a bit of feedback: Good:  You pronounced the g sound a little too strongly.  In a natural, conversational good night, the g is very soft. Imagine you're barely saying it. Night: Your pronunciation of Night is excellent! Here's how I'd pronounce it:[ɡʊd ˈNɔːɪgt]Try practicing saying it with a softer g and a slight emphasis on the first syllable of morning. Keep practicing, you're doing great! "
        }
        RetrofitClient.instance3.transcribe(referencePassageBody, filePart).enqueue(object : Callback<TranscriptionResponse> {
            override fun onResponse(call: Call<TranscriptionResponse>, response: Response<TranscriptionResponse>) {
                if (response.isSuccessful) {
                    val transcriptionResponse = response.body()
                    if (transcriptionResponse != null) {
                        Toast.makeText(this@Game, "Transcription: ${transcriptionResponse.transcription}", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this@Game, "Accuracy: ${transcriptionResponse.accuracy}", Toast.LENGTH_SHORT).show()
                        // Handle feedback and wrong words if needed
                    } else {
//                        Toast.makeText(this@Game, "Failed to get valid response", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@Game, "Audio sent successfully", Toast.LENGTH_SHORT).show()
                }


            }

            override fun onFailure(call: Call<TranscriptionResponse>, t: Throwable) {
//                Toast.makeText(this@Game, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }




    private fun hasPermissions(): Boolean {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            REQUEST_PERMISSION_CODE
        )
    }

    companion object {
        private const val SAMPLE_RATE = 16000
        private const val BUFFER_SIZE = 1024
        private const val REQUEST_PERMISSION_CODE = 1001
    }

    override fun onDestroy() {
        super.onDestroy()
        // Ensure audio recording is stopped and resources are released
        stopRecording()
    }
}