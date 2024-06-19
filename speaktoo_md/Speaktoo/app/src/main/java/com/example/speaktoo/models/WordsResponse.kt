package com.example.speaktoo.models

// WordsResponse.kt
data class WordsResponse(
    val status: String,
    val message: String,
    val data: List<Word>
)
