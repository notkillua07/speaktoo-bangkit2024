package com.example.speaktoo.api.base

import com.example.speaktoo.models.LoginResponse
import com.example.speaktoo.models.SignupResponse
import com.example.speaktoo.models.TranscriptionResponse
import com.example.speaktoo.models.User
import com.example.speaktoo.models.WordDetailResponse
import com.example.speaktoo.models.WordsResponse
import com.example.speaktoo.models.YourResponseClass
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("/email/signup")
    fun signup(@Body user: User): Call<SignupResponse>

    @POST("/email/login")
    fun login(@Body user: User): Call<LoginResponse>

    @POST("/words/{difficulty}")
    fun getWordsByDifficulty(@Path("difficulty") difficulty: String, @Body requestBody: Map<String, String>): Call<WordsResponse>

    @GET("/word/{word}")
    fun getWordDetails(@Path("word") word: String): Call<WordDetailResponse>

    @POST("/generate")
    fun generateWord(@Body requestBody: RequestBody): Call<YourResponseClass>

    @Multipart
    @POST("/transcribe")
    fun transcribe(
        @Part("reference_passage") referencePassage: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<TranscriptionResponse>
}