package com.example.mymovies1.model


import androidx.annotation.Keep

@Keep
data class MovieModel(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)