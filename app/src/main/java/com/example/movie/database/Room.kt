package com.example.movie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DB_NAME = "movies"

@Database(
    entities = [DBMovie::class, DBMovieDetails::class],
    version = 1
)
abstract class MoviesDatabase: RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val detailsDao: MovieDetailsDao
}

private lateinit var INSTANCE: MoviesDatabase

fun getDatabase(context: Context): MoviesDatabase {
    if(!::INSTANCE.isInitialized) {
        INSTANCE = Room.databaseBuilder(context.applicationContext,
            MoviesDatabase::class.java,
            DB_NAME).build()
    }
    return INSTANCE
}