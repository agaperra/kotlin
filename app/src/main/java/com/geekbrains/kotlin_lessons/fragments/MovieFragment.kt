package com.geekbrains.kotlin_lessons.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
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

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var movieViewModel: MovieViewModel
    private val movieAdapterPopular by lazy {
        HorizontalRecyclerAdapter(onItemViewClickListener = object : OnItemViewClickListener {
            override fun onItemClick(movie: Movie) {
                val action = MovieFragmentDirections.openMovie(movieId = movie.id)
                requireView().findNavController().navigate(action)
            }
        })
    }

    private val movieAdapterNowPlaying by lazy {
        HorizontalRecyclerAdapter(onItemViewClickListener = object : OnItemViewClickListener {
            override fun onItemClick(movie: Movie) {
                val action = MovieFragmentDirections.openMovie(movieId = movie.id)
                requireView().findNavController().navigate(action)
            }
        })
    }

    private val movieAdapterUpComing by lazy {
        HorizontalRecyclerAdapter(onItemViewClickListener = object : OnItemViewClickListener {
            override fun onItemClick(movie: Movie) {
                val action = MovieFragmentDirections.openMovie(movieId = movie.id)
                requireView().findNavController().navigate(action)
            }
        })
    }

    private val movieAdapterTop by lazy {
        HorizontalRecyclerAdapter(onItemViewClickListener = object : OnItemViewClickListener {
            override fun onItemClick(movie: Movie) {
                val action = MovieFragmentDirections.openMovie(movieId = movie.id)
                requireView().findNavController().navigate(action)
            }
        })
    }


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
        movieViewModel.apply {
            getObservedMoviesPopular().observe(viewLifecycleOwner, {
                movieAdapterPopular.clearItems()
                movieAdapterPopular.addItems(movies = it.results)
                movieAdapterPopular.notifyDataSetChanged()
                binding.isLoading = false
            })
            popularMovie()
        }
    }


    private fun getLookNowMovies() {
        binding.isLoading = true
        movieViewModel.apply {
            getObservedMoviesLookNow().observe(viewLifecycleOwner, {
                movieAdapterNowPlaying.clearItems()
                movieAdapterNowPlaying.addItems(movies = it.results)
                movieAdapterNowPlaying.notifyDataSetChanged()
                binding.isLoading = false
            })
            lookNowMovie()
        }
    }

    private fun getUpComingMovies() {
        binding.isLoading = true
        movieViewModel.apply {
            getObservedMoviesUpComing().observe(viewLifecycleOwner, {
                movieAdapterUpComing.clearItems()
                movieAdapterUpComing.addItems(movies = it.results)
                movieAdapterUpComing.notifyDataSetChanged()
                binding.isLoading = false
            })
            upComingMovie()
        }
    }

    private fun getTopMovies() {
        binding.isLoading = true
        movieViewModel.apply {
            getObservedMoviesTop().observe(viewLifecycleOwner, {
                movieAdapterTop.clearItems()
                movieAdapterTop.addItems(movies = it.results)
                movieAdapterTop.notifyDataSetChanged()
                binding.isLoading = false
            })
            topMovie()
        }
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


