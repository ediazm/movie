package com.example.movie.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movie.database.MoviesDatabase
import com.example.movie.database.asDomainModel
import com.example.movie.domain.Movie
import com.example.movie.domain.MovieDetails
import com.example.movie.network.API
import com.example.movie.network.IMDBMovieDetails
import com.example.movie.network.MovieAPIStatus
import com.example.movie.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class MoviesRepository(private val database: MoviesDatabase) {

    private val _status = MutableLiveData<MovieAPIStatus>()
    val status: LiveData<MovieAPIStatus>
        get() = _status

    init {
        _status.value = MovieAPIStatus.DONE
    }
    suspend fun searchMovies(query: String): List<Movie> {
        _status.value = MovieAPIStatus.LOADING
        return withContext(Dispatchers.IO) {
            try {
                var results = API.movies.getMovies(1, "json", query)
                database.movieDao.insertAll(results.asDatabaseModel())
                withContext(Dispatchers.Main){_status.value = MovieAPIStatus.DONE}
            } catch (e: IOException) {
                withContext(Dispatchers.Main){_status.value = MovieAPIStatus.ERROR}
            }
            val dbCache = database.movieDao.getMovies("%$query%")
            return@withContext dbCache.asDomainModel()
        }
    }


    suspend fun getMovieDetails(imdbID: String): MovieDetails? {
        _status.value = MovieAPIStatus.LOADING
        return withContext(Dispatchers.IO){
            try{
                var result: IMDBMovieDetails = API.movies.getMovieDetails(imdbID)
                result?.let{
                    database.detailsDao.insert(result.asDatabaseModel())
                }
                withContext(Dispatchers.Main){_status.value = MovieAPIStatus.DONE}
            } catch(e: IOException){
                withContext(Dispatchers.Main) {_status.value = MovieAPIStatus.ERROR}
            }

            val dbCache = database.detailsDao.get(imdbID)
            return@withContext dbCache?.asDomainModel()
        }
    }
}