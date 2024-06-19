package com.example.speaktoo.models

data class WordDetailResponse(
    val status: String,
    val message: String,
    val data: WordDetail
)

data class WordDetail(
    val audio: String,
    val result: List<WordResult>
)

data class WordResult(
    val word: String,
    val meaning: List<Meaning>
)

data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>
)

data class Definition(
    val definition: String,
    val synonyms: List<String>,
    val antonyms: List<String>,
    val example: String?
)
