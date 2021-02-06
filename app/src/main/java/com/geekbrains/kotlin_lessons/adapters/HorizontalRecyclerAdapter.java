package com.geekbrains.kotlin_lessons.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.geekbrains.kotlin_lessons.databinding.ItemMovieListBinding;

import com.geekbrains.kotlin_lessons.R;
import com.geekbrains.kotlin_lessons.models.Movie;

import java.util.List;

public class HorizontalRecyclerAdapter extends RecyclerView.Adapter<HorizontalRecyclerAdapter.MovieViewHolder>{

    private final List<Movie> movies;
    private LayoutInflater layoutInflater;

    public HorizontalRecyclerAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater==null){
            layoutInflater=LayoutInflater.from(parent.getContext());
        }
        ItemMovieListBinding movieListBinding= DataBindingUtil.inflate(layoutInflater,R.layout.item_movie_list, parent, false);
        return new MovieViewHolder(movieListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bindMovie(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ItemMovieListBinding itemMovieListBinding;

        public MovieViewHolder(ItemMovieListBinding itemMovieListBinding){
            super(itemMovieListBinding.getRoot());
            this.itemMovieListBinding=itemMovieListBinding;
        }

        public void bindMovie(Movie movie){
            itemMovieListBinding.setMovie(movie);
            itemMovieListBinding.executePendingBindings();
        }

    }
}
