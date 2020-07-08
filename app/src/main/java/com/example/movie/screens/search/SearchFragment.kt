package com.example.movie.screens.search

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movie.R
import com.example.movie.databinding.FragmentSearchBinding
import com.example.movie.network.MovieAPIStatus
import com.google.android.material.snackbar.Snackbar


class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.searchViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = MovieQueryAdapter(MovieClickListener { imdbId ->
            viewModel.onMovieSelected(imdbId)
        })

        binding.movieList.adapter = adapter

        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            when(status){
                MovieAPIStatus.LOADING -> {
                    val inputMethodManager =
                        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(binding.searchBtn.windowToken, 0)
                    binding.movieQuery.clearFocus()
                }
                MovieAPIStatus.ERROR -> {
                    Snackbar.make(binding.root, R.string.network_error, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        viewModel.navigateToMovieDetails.observe(viewLifecycleOwner, Observer { imdbID ->
            imdbID?.let {
                findNavController().navigate(
                    SearchFragmentDirections
                        .actionViewDetails(imdbID))
                viewModel.onMovieSelectedDone()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.sort_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if (viewModel.sort.value == SortBy.YearDec) {
            menu.findItem(R.id.sort_desc).isVisible = false
            menu.findItem(R.id.sort_asc).isVisible = true
        } else {
            menu.findItem(R.id.sort_desc).isVisible = true
            menu.findItem(R.id.sort_asc).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_desc, R.id.sort_asc -> {
                viewModel.toggleSort()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}