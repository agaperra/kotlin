package com.geekbrains.kotlin_lessons.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.adapters.HorizontalRecyclerAdapter
import com.geekbrains.kotlin_lessons.databinding.FragmentMovieBinding
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractorImpl
import com.geekbrains.kotlin_lessons.models.Movie
import com.geekbrains.kotlin_lessons.responses.MovieResponse
import com.geekbrains.kotlin_lessons.viewModels.MovieViewModel
import java.util.*
import kotlin.collections.ArrayList

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var movieViewModel: MovieViewModel
    private val moviesPopular: ArrayList<Movie> = ArrayList()
    private val moviesNowPlaying: ArrayList<Movie> = ArrayList()
    private val moviesUpComing: ArrayList<Movie> = ArrayList()
    private val moviesTop: ArrayList<Movie> = ArrayList()
    private lateinit var movieAdapterPopular: HorizontalRecyclerAdapter
    private lateinit var movieAdapterNowPlaying: HorizontalRecyclerAdapter
    private lateinit var movieAdapterUpComing: HorizontalRecyclerAdapter
    private lateinit var movieAdapterTop: HorizontalRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        doInitialization()
        return binding.root
    }


    private fun doInitialization() {
        binding.mainRecycler.setHasFixedSize(true)
        binding.lookingRecycler.setHasFixedSize(true)
        binding.upcomingRecycler.setHasFixedSize(true)
        binding.topRecycler.setHasFixedSize(true)
        movieViewModel=MovieViewModel(StringInteractorImpl(requireContext()))
        movieViewModel.liveDataPopular.observe(viewLifecycleOwner, { binding.textView2.text = it })
        movieViewModel.liveDataNowPlaying.observe(viewLifecycleOwner, { binding.textLookNow.text = it })
        movieViewModel.liveDataUpComing.observe(viewLifecycleOwner, { binding.textUpComingNow.text = it })
        movieViewModel.liveDataTop.observe(viewLifecycleOwner, { binding.textTop.text = it })
        movieAdapterPopular = HorizontalRecyclerAdapter(moviesPopular)
        movieAdapterNowPlaying = HorizontalRecyclerAdapter(moviesNowPlaying)
        movieAdapterUpComing=HorizontalRecyclerAdapter(moviesUpComing)
        movieAdapterTop=HorizontalRecyclerAdapter(moviesTop)
        binding.viewModel = movieViewModel
        binding.mainRecycler.adapter = movieAdapterPopular
        binding.lookingRecycler.adapter =movieAdapterNowPlaying
        binding.upcomingRecycler.adapter =movieAdapterUpComing
        binding.topRecycler.adapter =movieAdapterTop
        getPopularMovies()
        getLookNowMovies()
        getUpComingMovies()
        getTopMovies()

    }

    private fun getPopularMovies() {
        binding.isLoading = true
        movieViewModel.popularMovie.observe(viewLifecycleOwner,
            { movieResponse ->
                if (movieResponse != null) {
                    moviesPopular.addAll(movieResponse.results)
                    movieAdapterPopular.notifyDataSetChanged()
                    binding.isLoading = false
                }
            })

    }

    private fun getLookNowMovies() {
        binding.isLoading = true
        movieViewModel.lookNowMovie.observe(viewLifecycleOwner,
            { movieResponse ->
                if (movieResponse != null) {
                    moviesNowPlaying.addAll(movieResponse.results)
                    movieAdapterNowPlaying.notifyDataSetChanged()
                    binding.isLoading = false
                }
            })

    }

    private fun getUpComingMovies() {
        binding.isLoading = true
        movieViewModel.upComingMovie.observe(viewLifecycleOwner,
            { movieResponse ->
                if (movieResponse != null) {
                    moviesUpComing.addAll(movieResponse.results)
                    movieAdapterUpComing.notifyDataSetChanged()
                    binding.isLoading = false
                }
            })

    }

    private fun getTopMovies() {
        binding.isLoading = true
        movieViewModel.topMovie.observe(viewLifecycleOwner,
            { movieResponse ->
                if (movieResponse != null) {
                    moviesTop.addAll(movieResponse.results)
                    movieAdapterTop.notifyDataSetChanged()
                    binding.isLoading = false
                }
            })

    }


}


