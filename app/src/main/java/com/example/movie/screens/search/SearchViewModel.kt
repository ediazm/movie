package com.example.movie.screens.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movie.database.getDatabase
import com.example.movie.domain.Movie
import com.example.movie.network.MovieAPIStatus
import com.example.movie.repository.MoviesRepository
import kotlinx.coroutines.*

enum class SortBy { YearAsc, YearDec }

class SearchViewModel(app: Application) : AndroidViewModel(app) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val vmScope = CoroutineScope(Dispatchers.Default)

    private val moviesRepository = MoviesRepository(getDatabase(app))

    var query = MutableLiveData<String>("")


    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _navigateToMovieDetails = MutableLiveData<String>()
    val navigateToMovieDetails: LiveData<String>
        get() = _navigateToMovieDetails

    private val _sort = MutableLiveData<SortBy>(SortBy.YearDec)
    val sort: LiveData<SortBy>
        get() = _sort

    val status: LiveData<MovieAPIStatus> get() = moviesRepository.status


    fun toggleSort() {
        _sort.value = if (_sort.value == SortBy.YearDec) SortBy.YearAsc else SortBy.YearDec
        sortMovies(movies.value ?: emptyList())
    }

    private fun sortMovies(list: List<Movie>) {
        vmScope.launch {
            val sortedList = when (_sort.value) {
                SortBy.YearAsc -> list.sortedWith(
                    yearAsc
                )
                SortBy.YearDec -> list.sortedWith(
                    yearDec
                )
                else -> _movies.value
            }
            withContext(Dispatchers.Main) {
                _movies.value = sortedList
            }
        }
    }

    fun onSearchStart() = uiScope.launch {
        query.value?.isNotBlank()?.let {
            val movies = moviesRepository.searchMovies(query.value!!)
            sortMovies(movies)
        }
    }

    fun onMovieSelected(imdbID: String){
        _navigateToMovieDetails.value = imdbID
    }

    fun onMovieSelectedDone(){
        _navigateToMovieDetails.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        vmScope.cancel()
    }



    companion object {
        val yearAsc = Comparator<Movie> { p0, p1 -> p0.year.compareTo(p1.year) }
        val yearDec = Comparator<Movie> { p0, p1 -> p1.year.compareTo(p0.year) }
    }
}
