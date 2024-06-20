package com.example.speaktoo.models

data class TranscriptionResponse(
    val transcription: String,
    val accuracy: Double,
    val feedback: List<String>,
    val wrong_words: List<String>
)