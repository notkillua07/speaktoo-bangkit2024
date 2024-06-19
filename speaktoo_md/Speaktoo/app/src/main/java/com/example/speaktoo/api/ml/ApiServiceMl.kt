package com.example.speaktoo.api.ml

import com.example.speaktoo.models.YourResponseClass
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServiceMl {
    @POST("/generate")
    fun generateWord(@Body requestBody: RequestBody, @Query("word") word: String): Call<YourResponseClass>
}