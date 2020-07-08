package com.example.movie.screens.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movie.R
import com.example.movie.databinding.FragmentMovieDetailsBinding
import com.example.movie.network.MovieAPIStatus
import com.google.android.material.snackbar.Snackbar

class MovieDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMovieDetailsBinding.inflate(inflater)

        val application = requireActivity().application
        val args = MovieDetailsFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = MovieDetailsViewModelFactory(application, args.imdbID)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            when(status){
                MovieAPIStatus.ERROR -> {
                    Snackbar.make(binding.root, R.string.network_error, Snackbar.LENGTH_LONG).show()
                }
            }
        })
        return binding.root
    }

}