//package com.example.speaktoo.pages
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.media.AudioFormat
//import android.media.AudioRecord
//import android.media.MediaRecorder
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.example.speaktoo.api.base.RetrofitClient
//import com.example.speaktoo.databinding.GameBinding
//import com.example.speaktoo.models.YourResponseClass
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.RequestBody.Companion.asRequestBody
//import java.io.File
//import java.io.FileOutputStream
//import java.io.IOException
//
//class Game : AppCompatActivity() {
//    private lateinit var binding: GameBinding
//    private var audioRecord: AudioRecord? = null
//    private var recordingThread: Thread? = null
//    private var isRecording = false
//    private var outputFile: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = GameBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Check permissions on activity start
//        if (!hasPermissions()) {
//            requestPermissions()
//        }
//
//        // Microphone button click listener
//        binding.microphone.setOnClickListener {
//            if (isRecording) {
//                stopRecording()
//            } else {
//                startRecording()
//            }
//        }
//
//        binding.arrowGame.setOnClickListener {
//            // Close the current activity
//            finish()
//        }
//    }
//
//    private fun startRecording() {
//        if (!hasPermissions()) {
//            requestPermissions()
//            return
//        }
//
//        val bufferSize = AudioRecord.getMinBufferSize(
//            SAMPLE_RATE,
//            AudioFormat.CHANNEL_IN_MONO,
//            AudioFormat.ENCODING_PCM_16BIT
//        )
//
//        try {
//            audioRecord = AudioRecord(
//                MediaRecorder.AudioSource.MIC,
//                SAMPLE_RATE,
//                AudioFormat.CHANNEL_IN_MONO,
//                AudioFormat.ENCODING_PCM_16BIT,
//                bufferSize
//            )
//
//            outputFile = "${externalCacheDir?.absolutePath}/recording.wav"
//
//            audioRecord?.startRecording()
//            isRecording = true
//
//            recordingThread = Thread(Runnable {
//                writeAudioDataToFile()
//            }, "AudioRecorder Thread")
//
//            recordingThread?.start()
//            binding.microphoneState.text = "Stop Recording"
//        } catch (e: Exception) {
//            Toast.makeText(this@Game, "Failed to start recording: ${e.message}", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun stopRecording() {
//        isRecording = false
//        audioRecord?.apply {
//            stop()
//            release()
//        }
//        recordingThread?.interrupt()
//        binding.microphoneState.text = "Start Recording"
//
//        // After recording is complete, send the WAV file to API
//        outputFile?.let { filePath ->
//            val audioFile = File(filePath)
//            sendAudioToAPI(audioFile)
//        }
//    }
//
//    private fun writeAudioDataToFile() {
//        val data = ByteArray(BUFFER_SIZE)
//        val file = File(outputFile)
//
//        try {
//            val outputStream = FileOutputStream(file)
//            while (isRecording) {
//                val read = audioRecord?.read(data, 0, BUFFER_SIZE) ?: 0
//                if (AudioRecord.ERROR_INVALID_OPERATION != read) {
//                    outputStream.write(data, 0, read)
//                }
//            }
//            outputStream.close()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun sendAudioToAPI(audioFile: File) {
//        val requestBody = audioFile.asRequestBody("audio/wav".toMediaTypeOrNull())
//
//        RetrofitClient.instance.generateWord(requestBody, "YourWordHere").enqueue(object : retrofit2.Callback<YourResponseClass> {
//            override fun onResponse(call: retrofit2.Call<YourResponseClass>, response: retrofit2.Response<YourResponseClass>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(this@Game, "Audio sent successfully", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this@Game, "Failed to send audio", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: retrofit2.Call<YourResponseClass>, t: Throwable) {
//                Toast.makeText(this@Game, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun hasPermissions(): Boolean {
//        return (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
//                == PackageManager.PERMISSION_GRANTED)
//    }
//
//    private fun requestPermissions() {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(Manifest.permission.RECORD_AUDIO),
//            REQUEST_PERMISSION_CODE
//        )
//    }
//
//    companion object {
//        private const val SAMPLE_RATE = 16000
//        private const val BUFFER_SIZE = 1024
//        private const val REQUEST_PERMISSION_CODE = 1001
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        // Ensure audio recording is stopped and resources are released
//        stopRecording()
//    }
//}
