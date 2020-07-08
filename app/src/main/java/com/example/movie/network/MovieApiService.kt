package com.example.movie.network

import com.example.movie.BuildConfig
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://movie-database-imdb-alternative.p.rapidapi.com/"

private val client = with(OkHttpClient.Builder()) {
    if (BuildConfig.DEBUG) {
        addInterceptor(OkHttpProfilerInterceptor())
    }
    build()
}

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

object API {
    val movies: MovieAPI by lazy {
        retrofit.create(MovieAPI::class.java)
    }
}




