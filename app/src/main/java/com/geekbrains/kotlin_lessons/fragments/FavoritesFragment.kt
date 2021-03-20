package com.geekbrains.kotlin_lessons.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.adapters.FavoriteAdapter
import com.geekbrains.kotlin_lessons.adapters.HistoryAdapter
import com.geekbrains.kotlin_lessons.adapters.OnItemViewClickListener
import com.geekbrains.kotlin_lessons.databinding.FragmentFavoritesBinding
import com.geekbrains.kotlin_lessons.databinding.FragmentMovieBinding
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractorImpl
import com.geekbrains.kotlin_lessons.models.Movie
import com.geekbrains.kotlin_lessons.receivers.NetworkConnectionReceiver
import com.geekbrains.kotlin_lessons.utils.AppState
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.utils.Variables
import com.geekbrains.kotlin_lessons.viewModels.FavoritesViewModel
import com.geekbrains.kotlin_lessons.viewModels.MovieViewModel
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var binding: FragmentFavoritesBinding
    private var refreshing = 2000L

    private lateinit var networkConnectionReceiver: NetworkConnectionReceiver
    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter(onItemViewClickListener = object : OnItemViewClickListener {
            override fun onItemClick(movie: Movie) {
                Variables.BOOLEAN = true
                val action =
                    FavoritesFragmentDirections.actionNavigationFavoritesToInfoFragment(movieId = movie.id)
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        favoritesViewModel = FavoritesViewModel()
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
        }, refreshing)
    }

    private fun doInitialization() {
        networkConnectionReceiver = NetworkConnectionReceiver()
        when (networkConnectionReceiver.checkInternet(requireContext())) {
            false -> {
                requireView().findNavController().navigate(R.id.disconnectMovie)
            }
            true -> {

                binding.favoriteRecycler.apply {
                    adapter = favoriteAdapter
                    layoutManager =
                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }
                favoritesViewModel.favoriteLiveData.observe(viewLifecycleOwner, {
                    renderData(it)
                    favoriteAdapter.notifyDataSetChanged()
                })
                favoritesViewModel.getAllFavorite()
                binding.viewModel = favoritesViewModel
            }
        }
    }


    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.favoriteRecycler.visibility = View.VISIBLE
                favoriteAdapter.setData(appState.movieData)
            }
            is AppState.Loading -> {
                binding.favoriteRecycler.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.favoriteRecycler.visibility = View.VISIBLE
                favoritesViewModel.getAllFavorite()
            }
        }
    }

}