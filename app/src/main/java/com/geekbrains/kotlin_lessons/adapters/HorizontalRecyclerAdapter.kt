package com.geekbrains.kotlin_lessons.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.adapters.HorizontalRecyclerAdapter.MovieViewHolder
import com.geekbrains.kotlin_lessons.databinding.ItemMovieListBinding
import com.geekbrains.kotlin_lessons.models.Movie

class HorizontalRecyclerAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieViewHolder>() {
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val movieListBinding: ItemMovieListBinding = DataBindingUtil.inflate(
            layoutInflater!!, R.layout.item_movie_list, parent, false
        )
        return MovieViewHolder(movieListBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MovieViewHolder(private val itemMovieListBinding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(
            itemMovieListBinding.root
        ) {
        fun bindMovie(movie: Movie?) {
            itemMovieListBinding.movie = movie
            itemMovieListBinding.executePendingBindings()
        }
    }
}