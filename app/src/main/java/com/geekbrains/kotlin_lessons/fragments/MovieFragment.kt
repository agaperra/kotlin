package com.geekbrains.kotlin_lessons.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.adapters.HorizontalRecyclerAdapter
import com.geekbrains.kotlin_lessons.adapters.OnItemViewClickListener
import com.geekbrains.kotlin_lessons.databinding.FragmentMovieBinding
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractorImpl
import com.geekbrains.kotlin_lessons.models.Movie
import com.geekbrains.kotlin_lessons.viewModels.MovieViewModel
import kotlin.collections.ArrayList

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var movieViewModel: MovieViewModel
    private var movieAdapterPopular = HorizontalRecyclerAdapter(onItemViewClickListener = object : OnItemViewClickListener {
        override fun onItemClick(movie: Movie) {
            val action = MovieFragmentDirections.openMovie(movieId = movie.id)
            requireView().findNavController().navigate(action)
        }
    })
    private var movieAdapterNowPlaying = HorizontalRecyclerAdapter(onItemViewClickListener = object : OnItemViewClickListener {
        override fun onItemClick(movie: Movie) {
            val action = MovieFragmentDirections.openMovie(movieId = movie.id)
            requireView().findNavController().navigate(action)
        }
    })

    private var movieAdapterUpComing = HorizontalRecyclerAdapter(onItemViewClickListener = object : OnItemViewClickListener {
        override fun onItemClick(movie: Movie) {
            val action = MovieFragmentDirections.openMovie(movieId = movie.id)
            requireView().findNavController().navigate(action)
        }
    })

    private var movieAdapterTop = HorizontalRecyclerAdapter(onItemViewClickListener = object : OnItemViewClickListener {
        override fun onItemClick(movie: Movie) {
            val action = MovieFragmentDirections.openMovie(movieId = movie.id)
            requireView().findNavController().navigate(action)
        }
    })


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

        binding.mainRecycler.apply {
            adapter = movieAdapterPopular
            layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

        binding.lookingRecycler.apply {
            adapter = movieAdapterNowPlaying
            layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

        binding.upcomingRecycler.apply {
            adapter = movieAdapterUpComing
            layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

        binding.topRecycler.apply {
            adapter = movieAdapterTop
            layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

        movieViewModel = MovieViewModel(StringInteractorImpl(requireContext()))
        setUpLiveData()

        binding.viewModel = movieViewModel

        getPopularMovies()
        getLookNowMovies()
        getUpComingMovies()
        getTopMovies()

    }

    private fun getPopularMovies() {
        binding.isLoading = true
        movieViewModel.getObservedMoviesPopular().observe(viewLifecycleOwner, {
            binding.isLoading = true
            movieAdapterPopular.clearItems()
            movieAdapterPopular.addItems(movies = it.results)
            movieAdapterPopular.notifyDataSetChanged()
            binding.isLoading = false
        })
        movieViewModel.popularMovie()
    }


    private fun getLookNowMovies() {
        binding.isLoading = true
        movieViewModel.getObservedMoviesLookNow().observe(viewLifecycleOwner, {
            binding.isLoading = true
            movieAdapterNowPlaying.clearItems()
            movieAdapterNowPlaying.addItems(movies = it.results)
            movieAdapterNowPlaying.notifyDataSetChanged()
            binding.isLoading = false
        })
        movieViewModel.lookNowMovie()
    }

    private fun getUpComingMovies() {
        binding.isLoading = true
        movieViewModel.getObservedMoviesUpComing().observe(viewLifecycleOwner, {
            binding.isLoading = true
            movieAdapterUpComing.clearItems()
            movieAdapterUpComing.addItems(movies = it.results)
            movieAdapterUpComing.notifyDataSetChanged()
            binding.isLoading = false
        })
        movieViewModel.upComingMovie()
    }

    private fun getTopMovies() {
        binding.isLoading = true
        movieViewModel.getObservedMoviesTop().observe(viewLifecycleOwner, {
            binding.isLoading = true
            movieAdapterTop.clearItems()
            movieAdapterTop.addItems(movies = it.results)
            movieAdapterTop.notifyDataSetChanged()
            binding.isLoading = false
        })
        movieViewModel.topMovie()
    }


    private fun setUpLiveData() {
        movieViewModel.liveDataPopular.observe(viewLifecycleOwner, { binding.textView2.text = it })
        movieViewModel.liveDataNowPlaying.observe(
                viewLifecycleOwner,
                { binding.textLookNow.text = it })
        movieViewModel.liveDataUpComing.observe(
                viewLifecycleOwner,
                { binding.textUpComingNow.text = it })
        movieViewModel.liveDataTop.observe(viewLifecycleOwner, { binding.textTop.text = it })
    }


}


