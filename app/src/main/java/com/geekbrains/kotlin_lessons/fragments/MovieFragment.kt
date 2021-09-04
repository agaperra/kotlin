package com.geekbrains.kotlin_lessons.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.activity.MainActivity
import com.geekbrains.kotlin_lessons.activity.MainActivity.Companion.preferencesManager
import com.geekbrains.kotlin_lessons.adapters.HorizontalRecyclerAdapter
import com.geekbrains.kotlin_lessons.adapters.OnItemViewClickListener
import com.geekbrains.kotlin_lessons.databinding.FragmentMovieBinding
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractorImpl
import com.geekbrains.kotlin_lessons.models.Movie
import com.geekbrains.kotlin_lessons.receivers.NetworkConnectionReceiver
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.utils.SharedPreferencesManager
import com.geekbrains.kotlin_lessons.utils.Variables
import com.geekbrains.kotlin_lessons.viewModels.MovieViewModel


class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var networkConnectionReceiver: NetworkConnectionReceiver
    lateinit var sPrefs: SharedPreferencesManager

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
        sPrefs = preferencesManager!!
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
                getPopularMovies()
                getLookNowMovies()
                getUpComingMovies()
                getTopMovies()
                switchCheckEnter(Constants.TAG_THEME, Constants.THEME_DARK)

                binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->

                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        sPrefs.storeInt(Constants.TAG_THEME, Constants.THEME_DARK)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        sPrefs.storeInt(Constants.TAG_THEME, Constants.THEME_LIGHT)
                    }
                }

//
//                FirebaseInstanceId.getInstance().instanceId
//                    .addOnCompleteListener(OnCompleteListener { task ->
//                        if (!task.isSuccessful) {
//                            // Не удалось получить токен, произошла ошибка
//                            return@OnCompleteListener
//                        }
//
//                        // Получить токен
//                        val token = task.result!!.token
//                        print(token)
//                        // Сохранить токен...
//                    })

            }
        }

    }

    private fun switchCheckEnter(tag: String?, defValue: Int) {
        when (sPrefs.retrieveInt(tag, defValue)) {
            0 -> {
                binding.themeSwitch.isChecked = true
            }
            1 -> {
                binding.themeSwitch.isChecked = false
            }

        }
    }

    private fun getPopularMovies() {
        binding.isLoading = true
        movieViewModel.apply {
            getObservedMoviesPopular().observe(viewLifecycleOwner, { movieResponse ->
                movieAdapterPopular.clearItems()
                movieResponse.results.let {
                    movieAdapterPopular.addItems(it)
                    movieAdapterPopular.notifyDataSetChanged()
                    binding.isLoading = false
                }

            })
            popularMovie()
        }
    }


    private fun getLookNowMovies() {
        binding.isLoading = true
        movieViewModel.apply {
            getObservedMoviesLookNow().observe(viewLifecycleOwner, { movieResponse ->
                movieAdapterNowPlaying.clearItems()
                movieResponse.results.let {
                    movieAdapterNowPlaying.addItems(it)
                    movieAdapterNowPlaying.notifyDataSetChanged()
                    binding.isLoading = false
                }
            })
            lookNowMovie()
        }
    }

    private fun getUpComingMovies() {
        binding.isLoading = true
        movieViewModel.apply {
            getObservedMoviesUpComing().observe(viewLifecycleOwner, { movieResponse ->
                movieAdapterUpComing.clearItems()
                movieResponse.results.let {
                    movieAdapterUpComing.addItems(it)
                    movieAdapterUpComing.notifyDataSetChanged()
                    binding.isLoading = false
                }
            })
            upComingMovie()
        }
    }

    private fun getTopMovies() {
        binding.isLoading = true
        movieViewModel.apply {
            getObservedMoviesTop().observe(viewLifecycleOwner, { movieResponse ->
                movieAdapterTop.clearItems()
                movieResponse.results.let {
                    movieAdapterTop.addItems(it)
                    movieAdapterTop.notifyDataSetChanged()
                    binding.isLoading = false

                }
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


