package com.example.speaktoo.models

data class YourResponseClass(
    val status: String,
    val message: String,
    val data: AudioData
)

data class AudioData(
    val url: String
)

