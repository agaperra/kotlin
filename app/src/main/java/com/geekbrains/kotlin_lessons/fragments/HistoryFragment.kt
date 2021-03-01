package com.geekbrains.kotlin_lessons.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import com.geekbrains.kotlin_lessons.adapters.HistoryAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.adapters.HorizontalRecyclerAdapter
import com.geekbrains.kotlin_lessons.adapters.OnItemViewClickListener
import com.geekbrains.kotlin_lessons.databinding.FragmentHistoryBinding
import com.geekbrains.kotlin_lessons.models.Movie
import com.geekbrains.kotlin_lessons.receivers.NetworkConnectionReceiver
import com.geekbrains.kotlin_lessons.utils.AppState
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.viewModels.HistoryViewModel
import com.google.android.material.snackbar.Snackbar

class HistoryFragment : Fragment() {


    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var networkConnectionReceiver: NetworkConnectionReceiver
    private val historyAdapter: HistoryAdapter by lazy {
        HistoryAdapter(onItemViewClickListener = object : OnItemViewClickListener {
            override fun onItemClick(movie: Movie) {
                Constants.BOOLEAN = true
                val action =
                    HistoryFragmentDirections.actionNavigationHistoryToInfoFragment(movieId = movie.id)
                requireView().findNavController().navigate(action)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Constants.BOOLEAN = false
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        historyViewModel = HistoryViewModel()
        Constants.ADULT = historyViewModel.getPref()
        when (Constants.ADULT) {
            true -> binding.adultContent.isChecked = true
            false -> binding.adultContent.isChecked = false
        }
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


                binding.adultContent.setOnCheckedChangeListener { _, _ ->
                    when (binding.adultContent.isChecked) {
                        true -> {
                            Constants.ADULT = true
                            historyViewModel.setPref(Constants.ADULT)
                        }
                        false -> {
                            Constants.ADULT = false
                            historyViewModel.setPref(Constants.ADULT)
                        }
                    }

                    val snackbar =
                        Snackbar.make(binding.root, getString(R.string.adult), Snackbar.LENGTH_LONG)
                    @SuppressLint("InflateParams")
                    val customSnackView: View =
                        layoutInflater.inflate(R.layout.rounded, null)
                    snackbar.view.setBackgroundColor(Color.TRANSPARENT)
                    val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

                    snackbarLayout.setPadding(20, 20, 20, 20)
                    snackbarLayout.addView(customSnackView, 0)
                    snackbar.show()
                }


                binding.history.apply {
                    adapter = historyAdapter
                    layoutManager =
                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }
                historyViewModel.historyLiveData.observe(viewLifecycleOwner, {
                    renderData(it)
                    historyAdapter.notifyDataSetChanged()
                })
                historyViewModel.getAllHistory()
                binding.viewModel = historyViewModel


            }
        }
    }


    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.history.visibility = View.VISIBLE
                historyAdapter.setData(appState.movieData)
            }
            is AppState.Loading -> {
                binding.history.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.history.visibility = View.VISIBLE
                historyViewModel.getAllHistory()
            }
        }
    }

}