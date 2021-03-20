package com.geekbrains.kotlin_lessons.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.adapters.HorizontalRecyclerAdapter
import com.geekbrains.kotlin_lessons.adapters.OnItemViewClickListener
import com.geekbrains.kotlin_lessons.databinding.FragmentMovieBinding
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractorImpl
import com.geekbrains.kotlin_lessons.models.Genres
import com.geekbrains.kotlin_lessons.models.Movie
import com.geekbrains.kotlin_lessons.models.MovieFull
import com.geekbrains.kotlin_lessons.models.ProductionCountries
import com.geekbrains.kotlin_lessons.receivers.NetworkConnectionReceiver
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.utils.Variables
import com.geekbrains.kotlin_lessons.viewModels.MovieViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import java.util.ArrayList


class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var networkConnectionReceiver: NetworkConnectionReceiver

    private val movieAdapterPopular by lazy {
        HorizontalRecyclerAdapter(onItemViewClickListener = object : OnItemViewClickListener {
            override fun onItemClick(movie: Movie) {
                Variables.BOOLEAN = true
                val action = MovieFragmentDirections.openMovie(movieId = movie.id)
                requireView().findNavController().navigate(action)
            }
        })
    }

    private val movieAdapterNowPlaying by lazy {
        HorizontalRecyclerAdapter(onItemViewClickListener = object : OnItemViewClickListener {
            override fun onItemClick(movie: Movie) {
                Variables.BOOLEAN = true
                val action = MovieFragmentDirections.openMovie(movieId = movie.id)
                requireView().findNavController().navigate(action)
            }
        })
    }

    private val movieAdapterUpComing by lazy {
        HorizontalRecyclerAdapter(onItemViewClickListener = object : OnItemViewClickListener {
            override fun onItemClick(movie: Movie) {
                Variables.BOOLEAN = true
                val action = MovieFragmentDirections.openMovie(movieId = movie.id)
                requireView().findNavController().navigate(action)
            }
        })
    }

    private val movieAdapterTop by lazy {
        HorizontalRecyclerAdapter(onItemViewClickListener = object : OnItemViewClickListener {
            override fun onItemClick(movie: Movie) {
                Variables.BOOLEAN = true
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
        Variables.BOOLEAN = false
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)

        movieViewModel = MovieViewModel(StringInteractorImpl(requireContext()))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.refresh.setOnRefreshListener {
            refresh(binding.refresh)
        }
        doInitialization()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun refresh(swipeRefreshLayout: SwipeRefreshLayout) {
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.postOnAnimationDelayed({
            doInitialization()
            swipeRefreshLayout.isRefreshing = false
        }, 2000)
    }





    private fun doInitialization() {
        networkConnectionReceiver = NetworkConnectionReceiver()
        when (networkConnectionReceiver.checkInternet(requireContext())) {
            false -> {
                requireView().findNavController().navigate(R.id.disconnectMovie)
            }
            true -> {

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

                setUpLiveData()
                binding.viewModel = movieViewModel
                getFilms(0, movieAdapterPopular)
                getFilms(1, movieAdapterNowPlaying)
                getFilms(2, movieAdapterUpComing)
                getFilms(3, movieAdapterTop)
            }
        }

    }

    private fun getFilms(temp: Int, adapter: HorizontalRecyclerAdapter){
        binding.isLoading = true
        movieViewModel.apply {
            getObserving(temp).observe(viewLifecycleOwner, { movieResponse ->
                adapter.clearItems()
                movieResponse.results.let {
                    adapter.addItems(it)
                    adapter.notifyDataSetChanged()
                    binding.isLoading = false
                }

            })
            setFilms(temp)
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


