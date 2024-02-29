package com.example.mymovies1.service

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    // Create an Interceptor to add the API key as a query parameter to every request
    private val apiKeyInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()

        // Add API key as a query parameter
        val modifiedUrl: HttpUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", "85ed344ed43da94e70b48dc26233b9a8")
            .build()

        // Create a new request with the modified URL
        val newRequest = originalRequest.newBuilder()
            .url(modifiedUrl)
            .build()

        // Proceed with the new request
        chain.proceed(newRequest)
    }

    // Create OkHttpClient with the API key interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val movieApiService: MovieApiService = retrofit.create(MovieApiService::class.java)
}