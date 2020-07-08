package com.example.movie.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MovieDetailsDao{

    @Query("SELECT * FROM movie_details WHERE imdbID = :imdbID")
    fun get(imdbID: String): DBMovieDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieDetails: DBMovieDetails)
}