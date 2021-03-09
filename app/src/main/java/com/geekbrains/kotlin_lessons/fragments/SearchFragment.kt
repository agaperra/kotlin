package com.geekbrains.kotlin_lessons.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.adapters.OnItemViewClickListener
import com.geekbrains.kotlin_lessons.adapters.SearchActorsAdapter
import com.geekbrains.kotlin_lessons.adapters.SearchMovieAdapter
import com.geekbrains.kotlin_lessons.databinding.FragmentSearchBinding
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractorImpl
import com.geekbrains.kotlin_lessons.models.Movie
import com.geekbrains.kotlin_lessons.receivers.NetworkConnectionReceiver
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.utils.Variables
import com.geekbrains.kotlin_lessons.viewModels.SearchViewModel
import com.google.android.material.snackbar.Snackbar


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var networkConnectionReceiver: NetworkConnectionReceiver


    private val movieAdapterSearch by lazy {
        SearchMovieAdapter(onItemViewClickListener = object : OnItemViewClickListener {
            override fun onItemClick(movie: Movie) {
                Variables.BOOLEAN = true
                val action =
                        SearchFragmentDirections.actionNavigationSearchToInfoFragment(movieId = movie.id)
                requireView().findNavController().navigate(action)
            }
        })
    }

    private val actorAdapterSearch by lazy {
        SearchActorsAdapter()
    }
    private var data = ""

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        Variables.BOOLEAN = false
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        searchViewModel = SearchViewModel(StringInteractorImpl(requireContext()))

        Variables.ADULT = searchViewModel.getPref()
        binding.adultContent.isChecked = Variables.ADULT

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setUpSearchView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun doInitialization() {
        binding.adultContent.setOnCheckedChangeListener { _, _ ->
            when (binding.adultContent.isChecked) {
                true -> {
                    Variables.ADULT = true
                    searchViewModel.setPref(Variables.ADULT)
                }
                false -> {
                    Variables.ADULT = false
                    searchViewModel.setPref(Variables.ADULT)
                }
            }
            val snackbar =
                    Snackbar.make(binding.root, getString(R.string.adult), Snackbar.LENGTH_LONG)

            @SuppressLint("InflateParams")
            val customSnackView: View =
                    layoutInflater.inflate(R.layout.rounded, null)
            snackbar.view.setBackgroundColor(Color.TRANSPARENT)
            val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

            snackbarLayout.setPadding(R.dimen._20sdp, R.dimen._20sdp, R.dimen._20sdp, R.dimen._20sdp)
            snackbarLayout.addView(customSnackView, 0)
            snackbar.show()
        }
        binding.searchMovie.visibility = View.VISIBLE
        binding.searchActor.visibility = View.VISIBLE
        searchViewModel.liveDataPictures.observe(
                viewLifecycleOwner,
                { binding.textViewMovie.text = it })
        searchViewModel.liveDataActors.observe(
                viewLifecycleOwner,
                { binding.textViewActors.text = it })
        binding.viewModelSearch = searchViewModel

        binding.movieRecycler.apply {
            adapter = movieAdapterSearch
            layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

        binding.actorRecycler.apply {
            adapter = actorAdapterSearch
            layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
        setObserver(searchViewModel, movieAdapterSearch)
        setObserverActor(searchViewModel, actorAdapterSearch)


    }

    private fun setObserver(viewModel: SearchViewModel, adapter: SearchMovieAdapter) {
        binding.isLoading = true
        viewModel.getMovies().observe(viewLifecycleOwner, { movieResponse ->
            movieResponse?.let {
                adapter.clearItems()
                adapter.addItems(movieResponse.results)
                adapter.notifyDataSetChanged()
                binding.isLoading = false
            }
        })
    }


    private fun setObserverActor(viewModel: SearchViewModel, adapter: SearchActorsAdapter) {
        binding.isLoading = true
        viewModel.getActors().observe(viewLifecycleOwner, { actorsResponse ->
            actorsResponse?.let {
                adapter.clearItems()
                adapter.addItems(actorsResponse.results)
                adapter.notifyDataSetChanged()
                binding.isLoading = false
            }
        })
    }


    private fun setUpSearchView() {
        binding.searchView.apply {

            var id = context
                    .resources
                    .getIdentifier("android:id/search_src_text", null, null)
            val textView = findViewById<View>(id) as TextView
            //использование resources.getColor(id: Int, theme: Resources.Theme!) возможно с API 23, а у меня минимальная версия API 21
            textView.setTextColor(resources.getColor(R.color.bottom_nav_menu))
            val typeface = ResourcesCompat.getFont(requireContext(), R.font.normal)
            textView.typeface = typeface

            id = context
                    .resources
                    .getIdentifier("android:id/search_close_btn", null, null)
            var imageView = findViewById<View>(id) as ImageView
            imageView.setImageResource(R.drawable.ic_baseline_close_24)

            id = context
                    .resources
                    .getIdentifier("android:id/search_button", null, null)
            imageView = findViewById<View>(id) as ImageView
            imageView.setImageResource(R.drawable.ic_searcview)

            setOnClickListener { isIconified = false }

            networkConnectionReceiver = NetworkConnectionReceiver()
            when (networkConnectionReceiver.checkInternet(requireContext())) {
                false -> {
                    requireView().findNavController().navigate(R.id.disconnectSearch)
                }
                true -> {
                    setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String): Boolean {
                            return when (data.trim()) {
                                "" -> false
                                else -> {
                                    doInitialization()
                                    searchViewModel.textChanged(data)
                                    onActionViewCollapsed()
                                    true
                                }
                            }
                        }

                        override fun onQueryTextChange(newText: String): Boolean {
                            data = newText
                            doInitialization()
                            searchViewModel.textChanged(data)
                            return true
                        }
                    })
                }
            }

        }

    }

}