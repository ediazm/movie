package com.example.movie

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.movie.domain.Movie
import com.example.movie.network.MovieAPIStatus
import com.example.movie.screens.search.MovieQueryAdapter

@BindingAdapter("movieList")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieQueryAdapter
    adapter.submitList(data)
}

@BindingAdapter("isLoading")
fun bindProgressBar(progressBar: ProgressBar, status: MovieAPIStatus) {
    progressBar.visibility = if (status == MovieAPIStatus.LOADING) View.VISIBLE else View.GONE
}

@BindingAdapter("isNetworkError")
fun showOnNetworkError(imageView: ImageView, status: MovieAPIStatus){
    imageView.visibility = if (status == MovieAPIStatus.ERROR) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun bindImageUrl(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}