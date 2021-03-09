package com.geekbrains.kotlin_lessons.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.activity.MapsActivity
import com.geekbrains.kotlin_lessons.adapters.*
import com.geekbrains.kotlin_lessons.databinding.FragmentInfoBinding
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractorImpl
import com.geekbrains.kotlin_lessons.models.*
import com.geekbrains.kotlin_lessons.receivers.NetworkConnectionReceiver
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.utils.Variables
import com.geekbrains.kotlin_lessons.viewModels.InfoViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso


class InfoFragment : Fragment() {

    private lateinit var infoViewModel: InfoViewModel
    private lateinit var binding: FragmentInfoBinding
    private val args: InfoFragmentArgs by navArgs()
    private lateinit var genresAdapter: GenresAdapter
    private lateinit var movieFavorite: MovieFull

    private val actorsAdapter by lazy {
        ActorsAdapter(onItemViewClickListener = object : OnActorViewClickListener {
            override fun onItemClick(actor: Actor) {
                Variables.BOOLEAN = true
                val action = InfoFragmentDirections.actionNavigationInfoToActorFragment(actor.id)
                requireView().findNavController().navigate(action)
            }
        })
    }
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
        when (networkConnectionReceiver.checkInternet(context)) {
            false -> {
                when (flag) {
                    false -> requireView().findNavController().navigate(R.id.disconnectInfo)
                }
            }
        }
    }

    private fun createMovie(movie: MovieFull) =
            MovieFull(
                    movie.id,
                    "",
                    movie.overview,
                    movie.poster_path,
                    movie.release_date,
                    movie.title,
                    0.0,
                    0,
                    ArrayList<Genres>(),
                    ArrayList<ProductionCountries>(),
                    0,
                    0,
                    0,
                    "",
                    0.0
            )


    private fun saveMovie(movie: MovieFull) {
        infoViewModel.saveMovieToDB(
                createMovie(movie)
        )
    }

    private fun saveFavorite(movie: MovieFull) {
        infoViewModel.saveFavoriteToDB(
                createMovie(movie)
        )
    }

    private fun saveWatched(movie: MovieFull) {
        infoViewModel.saveWatchedToDB(movie)

    }

    private fun saveNote(movie: MovieFull, string: String) {
        infoViewModel.saveNoteToDB(movie, string)

    }


    private fun findFavorite(movie: MovieFull): Int {
        return infoViewModel.findFavorite(
                movie.id
        )
    }

    private fun findWatched(movie: MovieFull): Int {
        return infoViewModel.findWatched(
                movie.id
        )
    }

    private fun getNote(movie: MovieFull): Int {
        return infoViewModel.getId(
                movie.id
        )
    }

    private fun findNote(movie: MovieFull): String {
        return infoViewModel.getNote(
                movie.id
        )
    }

    private fun deleteNote(movie: MovieFull) {
        infoViewModel.deleteNoteFromDB(
                movie.id
        )
    }


    private fun deleteFavorite(movie: MovieFull) {
        infoViewModel.deleteFavoriteFromDB(
                movie.id
        )
    }


    private fun deleteWatched(movie: MovieFull) {
        infoViewModel.deleteWatchedFromDB(
                movie.id
        )
    }

    private fun doInitialization() {
        networkConnectionReceiver = NetworkConnectionReceiver()
        when (networkConnectionReceiver.checkInternet(context)) {
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


                binding.recyclerActors.apply {
                    adapter = actorsAdapter
                    layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                }

                lateinit var snackbar: Snackbar

                binding.watched.setOnClickListener {
                    when (binding.watched.tag) {
                        R.string.watched -> {
                            binding.watched.setImageResource(R.drawable.ic_baseline_remove_red_eye_24)
                            binding.watched.tag = R.string.no_watched
                            deleteWatched(movieFavorite)
                        }
                        R.string.no_watched -> {
                            binding.watched.setImageResource(R.drawable.ic_baseline_remove_red_eye_red_24)
                            binding.watched.tag = R.string.watched
                            saveWatched(movieFavorite)
                        }
                    }
                }

                binding.imageAdd.setOnClickListener {

                    val mDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                    val promptsView: View = LayoutInflater.from(context).inflate(
                            R.layout.prompt,
                            null
                    )
                    mDialogBuilder.setView(promptsView)
                    val edit: EditText = promptsView.findViewById(R.id.input_text)
                    edit.setText(binding.textAdd.text, TextView.BufferType.EDITABLE)
                    mDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton(
                                    "OK"
                            ) { _, _ ->
                                when {
                                    edit.text.toString().trim() != "" -> {
                                        binding.textAdd.text = edit.text
                                        saveNote(movieFavorite, edit.text.toString())
                                        binding.imageAdd.setImageResource(R.drawable.ic_baseline_drive_file_rename_outline_24)
                                    }
                                    edit.text.toString().trim() == "" && getNote(movieFavorite) == 1 -> {
                                        deleteNote(movieFavorite)
                                        binding.textAdd.text = getString(R.string.note)
                                        binding.imageAdd.setImageResource(R.drawable.ic_baseline_add_24)
                                    }
                                    else -> {
                                    }
                                }
                            }
                            .setNegativeButton(
                                    "Отмена"
                            ) { dialog, _ -> dialog.cancel() }


                    val alertDialog = mDialogBuilder.create()

                    alertDialog.show()
                }
                binding.like.setOnClickListener {

                    when (binding.like.tag) {
                        R.string.no_like -> {
                            binding.like.setImageResource(R.drawable.ic_baseline_favorite_24)
                            binding.like.tag = R.string.like
                            saveFavorite(movieFavorite)
                            snackbar =
                                    Snackbar.make(
                                            binding.root,
                                            R.string.add_to_favorites,
                                            Snackbar.LENGTH_SHORT
                                    )
                        }
                        else -> {
                            binding.like.setImageResource(R.drawable.ic_sharp_favorite_border_24)
                            binding.like.tag = R.string.no_like
                            deleteFavorite(movieFavorite)
                            snackbar =
                                    Snackbar.make(
                                            binding.root,
                                            R.string.remove_from_favorites,
                                            Snackbar.LENGTH_SHORT
                                    )
                        }
                    }
                    @SuppressLint("InflateParams") val customSnackView: View =
                            LayoutInflater.from(context).inflate(R.layout.rounded, null)
                    snackbar.view.setBackgroundColor(Color.TRANSPARENT)
                    val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

                    snackbarLayout.setPadding(R.dimen._20sdp, R.dimen._20sdp, R.dimen._20sdp, R.dimen._20sdp)
                    snackbarLayout.addView(customSnackView, 0)
                    snackbar.show()

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
            saveMovie(it)
            movieFavorite = it
            when (findFavorite(it)) {
                0 -> {
                    binding.like.setImageResource(R.drawable.ic_sharp_favorite_border_24)
                    binding.like.tag = R.string.no_like
                }
                1 -> {
                    binding.like.setImageResource(R.drawable.ic_baseline_favorite_24)
                    binding.like.tag = R.string.like
                }
            }

            when (findWatched(it)) {
                0 -> {
                    binding.watched.setImageResource(R.drawable.ic_baseline_remove_red_eye_24)
                    binding.watched.tag = R.string.no_watched
                }
                1 -> {
                    binding.watched.setImageResource(R.drawable.ic_baseline_remove_red_eye_red_24)
                    binding.watched.tag = R.string.watched
                }
            }

            when (getNote(it)) {
                0 -> {
                    binding.imageAdd.setImageResource(R.drawable.ic_baseline_add_24)
                    binding.imageAdd.tag = getString(R.string.note_false)
                }
                1 -> {
                    binding.textAdd.text = findNote(it)
                    binding.imageAdd.setImageResource(R.drawable.ic_baseline_drive_file_rename_outline_24)
                    binding.imageAdd.tag = getString(R.string.note_true)
                }
            }
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

        Picasso.get().load("${Constants.IMAGE_URL}${poster_path}")
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