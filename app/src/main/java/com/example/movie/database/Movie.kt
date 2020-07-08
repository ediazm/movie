package com.example.movie.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movie.domain.Movie

@Entity(tableName = "movie")
data class DBMovie(
    @PrimaryKey val imdbID: String,
    val title: String,
    val year: String,
    val type: String,
    val poster: String
)

fun List<DBMovie>.asDomainModel(): List<Movie> {
    return map {
        Movie(
            imdbID = it.imdbID,
            title = it.title,
            year = it.year,
            type = it.type,
            poster = it.poster
        )
    }
}
