package com.example.movie.network

import com.example.movie.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

enum class MovieAPIStatus { LOADING, ERROR, DONE }

interface MovieAPI {
    @Headers(BuildConfig.API_HOST, BuildConfig.API_KEY)
    @GET("/")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("r") r: String,
        @Query("s") s: String
    ): SearchResults

    @Headers(BuildConfig.API_HOST, BuildConfig.API_KEY)
    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") id: String,
        @Query("r") r: String = "json"
    ): IMDBMovieDetails
}