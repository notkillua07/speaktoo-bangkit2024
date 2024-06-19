package com.example.speaktoo.models

data class User(
    val email: String,
    val password: String,
    val username: String? = null
)
