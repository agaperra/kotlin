package com.geekbrains.kotlin_lessons.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.kotlin_lessons.Constants
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.adapters.HorizontalRecyclerAdapter.MovieViewHolder
import com.geekbrains.kotlin_lessons.databinding.ItemMovieListBinding
import com.geekbrains.kotlin_lessons.models.Movie
import com.squareup.picasso.Picasso


class HorizontalRecyclerAdapter(var onItemViewClickListener: OnItemViewClickListener) :
        RecyclerView.Adapter<MovieViewHolder>(), View.OnClickListener {

    private val moviesList = arrayListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_movie_list, parent, false)
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
            holder.bindMovie(moviesList[position])

    fun addItems(movies: ArrayList<Movie>) = this.moviesList.addAll(movies)

    fun clearItems() = this.moviesList.clear()

    override fun getItemCount() = moviesList.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("Recycle")
        fun bindMovie(movie: Movie) {

            itemView.apply {
                val poster: ImageView = findViewById(R.id.imageMovie)
                Picasso.get().load("${Constants.imageURL}${movie.poster_path}")
                        .placeholder(R.drawable.ic_baseline_image_not_supported_24)
                        .into(poster)

                findViewById<TextView>(R.id.textName).text = movie.title
                findViewById<TextView>(R.id.textReleaseDate).text = movie.release_date.substring(0, 4)

                val itemLike = findViewById<ImageView>(R.id.like)

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

                setOnClickListener {
                    onItemViewClickListener.onItemClick(movie = movie)
                }
            }
        }
    }

    override fun onClick(v: View?) {}

}