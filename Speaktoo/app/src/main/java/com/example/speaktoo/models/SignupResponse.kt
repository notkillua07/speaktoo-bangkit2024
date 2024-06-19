package com.example.speaktoo.models

data class SignupResponse(
    val status: String,
    val message: String,
    val data: UserData
)
