package com.example.mymovies1.service

import com.example.mymovies1.model.MovieModel
import retrofit2.http.GET

interface MovieApiService {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): MovieModel

    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieModel
}