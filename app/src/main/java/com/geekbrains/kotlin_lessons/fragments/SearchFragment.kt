package com.geekbrains.kotlin_lessons.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.adapters.SearchMovieAdapter
import com.geekbrains.kotlin_lessons.databinding.FragmentSearchBinding
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractorImpl
import com.geekbrains.kotlin_lessons.models.Movie
import com.geekbrains.kotlin_lessons.viewModels.SearchViewModel


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    private val moviesSearch: ArrayList<Movie> = ArrayList()
    private lateinit var movieAdapterSearch: SearchMovieAdapter
    private var data=""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        searchViewModel=SearchViewModel(StringInteractorImpl(requireContext()))
        setUpSearchView()
        return binding.root
    }

    private fun doInitialization() {
        binding.searchMovie.visibility=View.VISIBLE
        binding.movieRecycler.setHasFixedSize(true)
        searchViewModel=SearchViewModel(StringInteractorImpl(requireContext()))
        searchViewModel.liveDataPictures.observe(
            viewLifecycleOwner,
            { binding.textViewMovie.text = it })
        movieAdapterSearch = SearchMovieAdapter(moviesSearch)
        binding.viewModelSearch = searchViewModel
        binding.movieRecycler.adapter = movieAdapterSearch

        setObserver(searchViewModel, movieAdapterSearch)


    }

    private fun setObserver(viewModel: SearchViewModel, adapter: SearchMovieAdapter){
        binding.isLoading = true
        viewModel.getMovies().observe(viewLifecycleOwner, { movieResponse ->
            if (movieResponse != null) {
                adapter.clearItems()
                adapter.addItems(movieResponse.results)
                adapter.notifyDataSetChanged()
                binding.isLoading = false
            }
        })
    }


    private fun setUpSearchView() {
        val searchView = binding.searchView
        var id = searchView.context
                .resources
                .getIdentifier("android:id/search_src_text", null, null)
        val textView = searchView.findViewById<View>(id) as TextView
        textView.setTextColor(Color.parseColor("#D1A874"))
        textView.setHintTextColor(Color.parseColor("#D8C5AE"))

        id = searchView.context
                .resources
                .getIdentifier("android:id/search_close_btn", null, null)
        var imageView = searchView.findViewById<View>(id) as ImageView
        imageView.setImageResource(R.drawable.ic_baseline_close_24)

        id = searchView.context
                .resources
                .getIdentifier("android:id/search_button", null, null)
        imageView = searchView.findViewById<View>(id) as ImageView
        imageView.setImageResource(R.drawable.searcview_icon)


        searchView.setOnClickListener { searchView.isIconified = false }
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (data.trim { it <= ' ' } != "") {
                    doInitialization()
                    searchViewModel.textChanged(data)
                    searchView.onActionViewCollapsed()
                    return true
                }
                return false
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