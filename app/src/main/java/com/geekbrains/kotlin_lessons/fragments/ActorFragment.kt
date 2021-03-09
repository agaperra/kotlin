package com.geekbrains.kotlin_lessons.fragments


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.geekbrains.kotlin_lessons.adapters.AlsoKnownAsAdapter
import com.geekbrains.kotlin_lessons.databinding.FragmentActorsBinding
import com.geekbrains.kotlin_lessons.models.ActorFull
import com.geekbrains.kotlin_lessons.receivers.NetworkConnectionReceiver
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.viewModels.ActorsViewModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ActorFragment : Fragment() {

    private lateinit var actorViewModel: ActorsViewModel
    private lateinit var binding: FragmentActorsBinding
    private val args: ActorFragmentArgs by navArgs()
    private lateinit var alsoAdapter: AlsoKnownAsAdapter
    private lateinit var currentActor: ActorFull

    private lateinit var networkConnectionReceiver: NetworkConnectionReceiver
    private var flag: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_actors, container, false)
        actorViewModel =
            ActorsViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.refresh.setOnRefreshListener {
            refresh(binding.refresh)
        }
        setListenerPlace()
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
                    false -> requireView().findNavController().navigate(R.id.disconnectActor)
                }
            }
        }
    }


    private fun doInitialization() {
        networkConnectionReceiver = NetworkConnectionReceiver()
        when (networkConnectionReceiver.checkInternet(context)) {
            false -> {
                flag = true
                requireView().findNavController().navigate(R.id.disconnectActor)
            }
            true -> {
                flag = false
                lifecycle.addObserver(actorViewModel)
                actorViewModel.getDetails(args.actorId)
                startObserve()
                alsoAdapter = AlsoKnownAsAdapter()

                binding.recyclerViewAlsoKnown.apply {
                    adapter = alsoAdapter
                    layoutManager =
                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }
            }
        }


    }

    private fun startObserve() {
        binding.isLoading = true
        actorViewModel.getObservedActor().observe(viewLifecycleOwner, {
            setAlso(it.also_known_as)
            setName(it.name)
            setDate(it.birthday)
            setCountry(it.place_of_birth)
            setOverview(it.biography)
            setPoster(it.profile_path)
            binding.isLoading = false
            currentActor = it
        })

    }

    private fun setListenerPlace() {
        binding.birthAt.apply {
            paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
            setOnClickListener {
                binding.birthAt.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )

                Intent(activity, MapsActivity::class.java).also {
                    it.putExtra(Constants.ACTOR_PLACE_OF_BIRTH, currentActor.place_of_birth)
                    context.startActivity(it)
                }
            }
        }
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
        binding.biography.text = overview
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setDate(date: String) {
        val format = SimpleDateFormat()
        format.applyPattern("yyyy-mm-dd")
        val dt: Date = format.parse(date)
        val newDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        binding.birthDay.text = newDateFormat.format(dt)
    }


    @SuppressLint("SetTextI18n")
    private fun setCountry(name: String) {
        binding.birthAt.text = name
    }

    private fun setAlso(also: List<String>) {
        alsoAdapter.clearItems()
        alsoAdapter.setAlso(also)
        alsoAdapter.notifyDataSetChanged()
    }

}