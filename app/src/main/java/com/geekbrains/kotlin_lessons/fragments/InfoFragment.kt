package com.geekbrains.kotlin_lessons.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.geekbrains.kotlin_lessons.Constants
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.adapters.ActorsAdapter
import com.geekbrains.kotlin_lessons.adapters.GenresAdapter
import com.geekbrains.kotlin_lessons.databinding.FragmentInfoBinding
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractorImpl
import com.geekbrains.kotlin_lessons.models.Genres
import com.geekbrains.kotlin_lessons.receivers.NetworkConnectionReceiver
import com.geekbrains.kotlin_lessons.viewModels.InfoViewModel
import com.squareup.picasso.Picasso

class InfoFragment : Fragment() {

    private lateinit var infoViewModel: InfoViewModel
    private lateinit var binding: FragmentInfoBinding
    private val args: InfoFragmentArgs by navArgs()
    private lateinit var genresAdapter: GenresAdapter
    private lateinit var actorsAdapter: ActorsAdapter
    private lateinit var networkConnectionReceiver: NetworkConnectionReceiver
    private var flag: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        infoViewModel =
            InfoViewModel(StringInteractorImpl(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.refresh.setOnRefreshListener {
            refresh(binding.refresh)
        }
        when (activity?.supportFragmentManager?.findFragmentByTag("info")) {
            is InfoFragment -> {
            }
            else -> {
                val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
                ft?.addToBackStack("info")
                ft?.commit()
            }
        }
        doInitialization()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun refresh(swipeRefreshLayout: SwipeRefreshLayout) {
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.postOnAnimationDelayed({
            goBack()
            swipeRefreshLayout.isRefreshing = false
        }, 2000)
    }

    private fun goBack() {
        networkConnectionReceiver = NetworkConnectionReceiver()
        when (networkConnectionReceiver.checkInternet(requireContext())) {
            false -> {
                when (flag) {
                    false -> requireView().findNavController().navigate(R.id.disconnectInfo)
                }
            }
        }
    }

    private fun doInitialization() {
        networkConnectionReceiver = NetworkConnectionReceiver()
        when (networkConnectionReceiver.checkInternet(requireContext())) {
            false -> {
                flag = true
                requireView().findNavController().navigate(R.id.disconnectInfo)
            }
            true -> {
                flag = false
                lifecycle.addObserver(infoViewModel)
                infoViewModel.getDetails(args.movieId)
                startObserve()
                genresAdapter = GenresAdapter()

                binding.recyclerViewGenres.apply {
                    adapter = genresAdapter
                    layoutManager =
                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }

                actorsAdapter = ActorsAdapter()

                binding.recyclerActors.apply {
                    adapter = actorsAdapter
                    layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                }
            }
        }


    }

    private fun startObserve() {
        binding.isLoading = true
        infoViewModel.getObservedMovie().observe(viewLifecycleOwner, {
            setPoster(it.poster_path)
            setName(it.title)
            setDate(infoViewModel.getDate(it.release_date))
            setRating(it.vote_average)
            setRatingCount(it.vote_count)
            setBudget(it.budget)
            setRevenue(it.revenue)
            setCountry(infoViewModel.getCountry(it.production_countries))
            setOverview(infoViewModel.getOverview(it.overview))
            setRuntime(infoViewModel.getRuntime(it.runtime))
            setGenres(it.genres)
            binding.isLoading = false
        })

        infoViewModel.getPeople().observe(viewLifecycleOwner, {
            actorsAdapter.addItems(it.cast)
            actorsAdapter.notifyDataSetChanged()
        })
    }

    private fun setName(name: String) {
        binding.textView.text = name
    }


    private fun setPoster(poster_path: String?) {

        Picasso.get().load("${Constants.imageURL}${poster_path}")
            .placeholder(R.drawable.ic_baseline_image_not_supported_24)
            .into(binding.imageMovie)
    }

    private fun setOverview(overview: String) {
        binding.textOverview.text = overview
    }

    @SuppressLint("SetTextI18n")
    fun setRating(rating_average: Double) {
        binding.textRating.text = "$rating_average"
    }

    @SuppressLint("SetTextI18n")
    fun setRatingCount(rating_count: Int) {
        binding.textPeople.text = "$rating_count"
    }

    @SuppressLint("SetTextI18n")
    private fun setDate(date: String) {
        binding.date.text = date
    }

    @SuppressLint("SetTextI18n")
    fun setBudget(budget: Int) {
        binding.budget.text = "$$budget"
    }

    @SuppressLint("SetTextI18n")
    fun setRevenue(revenue: Int) {
        binding.revenue.text = "$$revenue"
    }

    @SuppressLint("SetTextI18n")
    private fun setRuntime(runtime: String) {
        binding.runtime.text = runtime
    }

    @SuppressLint("SetTextI18n")
    private fun setCountry(name: String) {
        binding.country.text = name
    }

    private fun setGenres(genres: List<Genres>) {
        genresAdapter.clearItems()
        genresAdapter.setGenres(genres = genres)
        genresAdapter.notifyDataSetChanged()
    }


}