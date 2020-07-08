package com.example.movie.screens.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MovieDetailsViewModelFactory(
    private val app: Application,
    private val imdbID: String
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(app, imdbID) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}