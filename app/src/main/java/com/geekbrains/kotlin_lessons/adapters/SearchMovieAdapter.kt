package com.geekbrains.kotlin_lessons.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.kotlin_lessons.Constants
import com.geekbrains.kotlin_lessons.Constants.Companion.imageURL
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.models.Movie
import com.squareup.picasso.Picasso

class SearchMovieAdapter(private val movies: ArrayList<Movie>,  var onItemViewClickListener: OnItemViewClickListener) :
    RecyclerView.Adapter<SearchMovieAdapter.MovieSearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieSearchViewHolder(
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_list, parent, false)
    )


    fun addItems(movies: ArrayList<Movie>) = this.movies.addAll(movies)

    fun clearItems() = this.movies.clear()

    override fun onBindViewHolder(holder: MovieSearchViewHolder, position: Int) =
        holder.bindMovie(movies[position])

    override fun getItemCount(): Int = movies.size

    inner class MovieSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.imageMovie)


        fun bindMovie(movie: Movie) {
            itemView.findViewById<ImageView>(R.id.like).visibility = View.INVISIBLE

            Picasso.get().load("${imageURL}${movie.poster_path}")
                .placeholder(R.drawable.ic_baseline_image_not_supported_24)
                .into(poster)

            itemView.findViewById<TextView>(R.id.textName).text = movie.title
            itemView.findViewById<TextView>(R.id.textReleaseDate).text = movie.release_date
            itemView.setOnClickListener {
                onItemViewClickListener.onItemClick(movie = movie)
            }

        }

    }

}