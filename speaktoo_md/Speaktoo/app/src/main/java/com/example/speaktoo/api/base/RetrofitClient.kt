package com.example.speaktoo.api.base

// RetrofitClient.kt
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL1 = "https://speaktoo-api-ygiysmsnnq-as.a.run.app"
    private const val BASE_URL2 = "https://speaktoo-generative-ygiysmsnnq-et.a.run.app"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttpClient instances
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val client2 = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Retrofit instances
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL1)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofit2 = Retrofit.Builder()
        .baseUrl(BASE_URL2)
        .client(client2)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // ApiService instances
    val instance: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val instance2: ApiService by lazy {
        retrofit2.create(ApiService::class.java)
    }
}

