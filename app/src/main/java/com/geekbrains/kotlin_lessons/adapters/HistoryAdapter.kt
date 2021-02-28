package com.geekbrains.kotlin_lessons.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.models.Movie
import com.geekbrains.kotlin_lessons.models.MovieFull
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.utils.showSnackBar
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class HistoryAdapter(
    var onItemViewClickListener: OnItemViewClickListener
) : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<MovieFull> = arrayListOf()

    fun setData(data: List<MovieFull>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favorite, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: MovieFull) {
            itemView.apply {
                findViewById<TextView>(R.id.textName).text = data.title
                findViewById<TextView>(R.id.textReleaseDate).text = data.release_date.take(4)
                val poster: ImageView = findViewById(R.id.imageMovie)
                Picasso.get().load("${Constants.IMAGE_URL}${data.poster_path}")
                    .placeholder(R.drawable.ic_baseline_image_not_supported_24)
                    .into(poster)

                setOnClickListener {
                    val movie: Movie = Movie(
                        data.id,
                        data.original_title,
                        data.overview,
                        data.poster_path,
                        data.release_date,
                        data.title
                    )
                    onItemViewClickListener.onItemClick(movie)
                }

                val like: ImageView = findViewById(R.id.like)
                like.tag = R.string.no_like
                lateinit var snackbar: Snackbar

                like.setOnClickListener {

                    when (like.tag) {
                        R.string.no_like -> {
                            like.setImageResource(R.drawable.ic_baseline_favorite_24)
                            like.tag = R.string.like

                            snackbar =
                                showSnackBar(R.string.add_to_favorites, Snackbar.LENGTH_SHORT)
                        }
                        else -> {
                            like.setImageResource(R.drawable.ic_sharp_favorite_border_24)
                            like.tag = R.string.no_like
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

            }


        }
    }

}