package com.geekbrains.kotlin_lessons.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.adapters.HorizontalRecyclerAdapter.MovieViewHolder
import com.geekbrains.kotlin_lessons.databinding.ItemMovieListBinding
import com.geekbrains.kotlin_lessons.models.Movie


class HorizontalRecyclerAdapter(private val movies: List<Movie>) :
        RecyclerView.Adapter<MovieViewHolder>(),  View.OnClickListener {

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
            val itemLike=itemMovieListBinding.like
            itemLike.tag=R.string.nolike
            itemLike.setOnClickListener{

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
                //
//                if(itemLike.drawable==(R.drawable.ic_baseline_favorite_24).toDrawable()){
//                    itemLike.background=(R.drawable.ic_sharp_favorite_border_24).toDrawable()
//                } else{
//                    itemLike.background=(R.drawable.ic_baseline_favorite_24).toDrawable()
//                }
            }
            itemMovieListBinding.movie = movie
            itemMovieListBinding.executePendingBindings()
        }
    }

    override fun onClick(v: View?) {}

}