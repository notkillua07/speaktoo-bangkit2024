package com.example.speaktoo.models

data class LoginResponse(
    val status: String,
    val message: String,
    val data: UserData
)
