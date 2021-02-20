package com.geekbrains.kotlin_lessons.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.models.Genres

class GenresAdapter : RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

    private val genre = arrayListOf<Genres>()

    inner class GenresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(genre: Genres) {
            itemView.findViewById<TextView>(R.id.genreItem).text = genre.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GenresViewHolder(
            itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.genre_item, parent, false)
    )

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) =
            holder.bind(genre = genre[position])

    override fun getItemCount() = genre.count()

    fun setGenres(genres: List<Genres>) = genre.addAll(genres)

    fun clearItems() = this.genre.clear()

}