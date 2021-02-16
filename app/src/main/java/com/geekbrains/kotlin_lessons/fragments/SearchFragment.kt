package com.geekbrains.kotlin_lessons.fragments

import android.graphics.Typeface
import android.graphics.fonts.FontFamily
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.activity.MainActivity
import com.geekbrains.kotlin_lessons.adapters.HorizontalRecyclerAdapter
import com.geekbrains.kotlin_lessons.adapters.OnItemViewClickListener
import com.geekbrains.kotlin_lessons.adapters.SearchMovieAdapter
import com.geekbrains.kotlin_lessons.databinding.FragmentSearchBinding
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractorImpl
import com.geekbrains.kotlin_lessons.models.Movie
import com.geekbrains.kotlin_lessons.receivers.NetworkConnectionReceiver
import com.geekbrains.kotlin_lessons.viewModels.SearchViewModel
import okhttp3.internal.Util


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var networkConnectionReceiver: NetworkConnectionReceiver

    private val movieAdapterSearch by lazy {
        SearchMovieAdapter(onItemViewClickListener = object : OnItemViewClickListener {
            override fun onItemClick(movie: Movie) {
                val action =
                    SearchFragmentDirections.actionNavigationSearchToInfoFragment(movieId = movie.id)
                requireView().findNavController().navigate(action)
            }
        })
    }
    private var data = ""

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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        searchViewModel = SearchViewModel(StringInteractorImpl(requireContext()))
        setUpSearchView()
        return binding.root
    }

    private fun doInitialization() {

        networkConnectionReceiver = NetworkConnectionReceiver()
        when (networkConnectionReceiver.checkInternet(requireContext())) {
            false -> {
                requireView().findNavController().navigate(R.id.disconnectSearch)
            }
            true -> {
                binding.searchMovie.visibility = View.VISIBLE

                searchViewModel = SearchViewModel(StringInteractorImpl(requireContext()))
                searchViewModel.liveDataPictures.observe(
                    viewLifecycleOwner,
                    { binding.textViewMovie.text = it })
                binding.viewModelSearch = searchViewModel

                binding.movieRecycler.apply {
                    adapter = movieAdapterSearch
                    layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                }
                setObserver(searchViewModel, movieAdapterSearch)
            }
        }


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
            imageView.setImageResource(R.drawable.searcview_icon)

            setOnClickListener { isIconified = false }
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