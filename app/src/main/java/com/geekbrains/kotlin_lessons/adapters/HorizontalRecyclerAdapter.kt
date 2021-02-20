package com.geekbrains.kotlin_lessons.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.kotlin_lessons.Constants
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.adapters.HorizontalRecyclerAdapter.MovieViewHolder
import com.geekbrains.kotlin_lessons.models.Movie
import com.geekbrains.kotlin_lessons.models.showSnackBar
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import java.util.*


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

        @SuppressLint("Recycle", "SimpleDateFormat", "InflateParams")
        fun bindMovie(movie: Movie) {

            itemView.apply {
                val poster: ImageView = findViewById(R.id.imageMovie)
                Picasso.get().load("${Constants.imageURL}${movie.poster_path}")
                    .placeholder(R.drawable.ic_baseline_image_not_supported_24)
                    .into(poster)

                findViewById<TextView>(R.id.textName).text = movie.title
                findViewById<TextView>(R.id.textReleaseDate).text =
                    movie.release_date.take(4)

                val itemLike = findViewById<ImageView>(R.id.like)
                itemLike.tag = R.string.nolike
                lateinit var snackbar: Snackbar

                itemLike.setOnClickListener {

                    when (itemLike.tag) {
                        R.string.nolike -> {
                            itemLike.setImageResource(R.drawable.ic_baseline_favorite_24)
                            itemLike.tag = R.string.like
                            snackbar =
                                showSnackBar(R.string.add_to_favorites, Snackbar.LENGTH_SHORT)
                        }
                        else -> {
                            itemLike.setImageResource(R.drawable.ic_sharp_favorite_border_24)
                            itemLike.tag = R.string.nolike
                            snackbar =
                                showSnackBar(R.string.remove_from_favorites, Snackbar.LENGTH_SHORT)
                        }
                    }
                    @SuppressLint("InflateParams") val customSnackView: View =
                        LayoutInflater.from(context).inflate(R.layout.rounded, null)
                    snackbar.view.setBackgroundColor(Color.TRANSPARENT)
                    val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

                    snackbarLayout.setPadding(20, 20, 20, 20)
                    snackbarLayout.addView(customSnackView, 0)
                    snackbar.show()
                }

                setOnClickListener {
                    onItemViewClickListener.onItemClick(movie = movie)
                }
            }
        }
    }


    override fun onClick(v: View?) {}

}