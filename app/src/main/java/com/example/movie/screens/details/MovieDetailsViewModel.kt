package com.example.movie.screens.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movie.database.getDatabase
import com.example.movie.domain.MovieDetails
import com.example.movie.network.MovieAPIStatus
import com.example.movie.repository.MoviesRepository
import kotlinx.coroutines.*

class MovieDetailsViewModel(app: Application, private val imdbID: String): AndroidViewModel(app){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val moviesRepository = MoviesRepository(getDatabase(app))

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails>
        get() = _movieDetails

    val status: LiveData<MovieAPIStatus> get() = moviesRepository.status

    init {
        getMovieDetails(imdbID)
    }

    private fun getMovieDetails(imdbID: String) = uiScope.launch {
        _movieDetails.value =  moviesRepository.getMovieDetails(imdbID)
    }

    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }
}