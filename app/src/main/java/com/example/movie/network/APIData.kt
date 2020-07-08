package com.example.movie.network

import com.example.movie.database.DBMovie
import com.example.movie.database.DBMovieDetails
import com.google.gson.annotations.SerializedName

data class SearchResults(
    val Search: List<IMDBMovie>?,
    val totalResults: String?,
    val Response: String,
    val Error: String?
)

fun SearchResults.asDatabaseModel(): List<DBMovie> {
    Search?.apply {
        return map {
            DBMovie(
                imdbID = it.imdbID,
                title = it.title,
                type = it.type,
                poster = it.poster,
                year = it.year
            )
        }
    }
    return emptyList()
}

data class IMDBMovie(
    val imdbID: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val poster: String
)


data class IMDBMovieDetails(
    val imdbID: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Rated") val rated: String,
    @SerializedName("Released") val released: String,
    @SerializedName("Runtime") val runtime: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Writer") val writer: String,
    @SerializedName("Actors") val actors: String,
    @SerializedName("Plot") val plot: String,
    @SerializedName("Language") val language: String,
    @SerializedName("Country") val country: String,
    @SerializedName("Awards") val awards: String,
    @SerializedName("Poster") val poster: String,
    @SerializedName("Type") val type: String
)

fun IMDBMovieDetails.asDatabaseModel(): DBMovieDetails {
    return DBMovieDetails(
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
