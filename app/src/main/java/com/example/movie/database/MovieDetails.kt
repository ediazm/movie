package com.example.movie.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movie.domain.MovieDetails

@Entity(tableName = "movie_details")
data class DBMovieDetails(
    @PrimaryKey val imdbID: String,
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val writer: String,
    val actors: String,
    val plot: String,
    val language: String,
    val country: String,
    val awards: String,
    val poster: String,
    val type: String
)

fun DBMovieDetails.asDomainModel(): MovieDetails {
    return MovieDetails(
        imdbID = imdbID,
        title = title,
        year = year,
        type = type,
        poster = poster,
        rated = rated,
        released = released,
        runtime = runtime,
        genre = genre,
        director = director,
        writer = writer,
        actors = actors,
        plot = plot,
        language = language,
        country = country,
        awards = awards
    )
}


fun List<DBMovieDetails>.asDomainModel(): List<MovieDetails> {
    return map {
        MovieDetails(
            imdbID = it.imdbID,
            title = it.title,
            year = it.year,
            type = it.type,
            poster = it.poster,
            rated = it.rated,
            released = it.released,
            runtime = it.runtime,
            genre = it.genre,
            director = it.director,
            writer = it.writer,
            actors = it.actors,
            plot = it.plot,
            language = it.language,
            country = it.country,
            awards = it.awards
        )
    }
}