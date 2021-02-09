package com.geekbrains.kotlin_lessons.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.kotlin_lessons.Constants
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.adapters.HorizontalRecyclerAdapter.MovieViewHolder
import com.geekbrains.kotlin_lessons.databinding.ItemMovieListBinding
import com.geekbrains.kotlin_lessons.models.Movie
import com.squareup.picasso.Picasso


class HorizontalRecyclerAdapter(private val movies: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieViewHolder>(), View.OnClickListener {

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

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bindMovie(movies[position])

    fun addItems(movies: ArrayList<Movie>) = this.movies.addAll(movies)

    fun clearItems() = this.movies.clear()

    override fun getItemCount() = movies.size

    class MovieViewHolder(private val itemMovieListBinding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(
            itemMovieListBinding.root
        ) {
        @SuppressLint("Recycle")
        fun bindMovie(movie: Movie) {

            val poster: ImageView = itemView.findViewById(R.id.imageMovie)
            Picasso.get().load("${Constants.imageURL}${movie.poster_path}")
                .placeholder(R.drawable.ic_baseline_image_not_supported_24)
                .into(poster)

            val itemLike = itemMovieListBinding.like

            itemLike.setOnClickListener {

                when (itemLike.tag) {
                    R.string.nolike -> {
                        itemLike.setImageResource(R.drawable.ic_baseline_favorite_24)
                        itemLike.tag = R.string.like
                    }
                    else -> {
                        itemLike.setImageResource(R.drawable.ic_sharp_favorite_border_24)
                        itemLike.tag = R.string.nolike
                    }
                }
            }
            itemMovieListBinding.movie = movie
            itemMovieListBinding.executePendingBindings()
        }
    }

    override fun onClick(v: View?) {}

}