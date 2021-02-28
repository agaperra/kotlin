package com.geekbrains.kotlin_lessons.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.databinding.FragmentFavoritesBinding
import com.geekbrains.kotlin_lessons.databinding.FragmentMovieBinding
import com.geekbrains.kotlin_lessons.interactors.string.StringInteractorImpl
import com.geekbrains.kotlin_lessons.receivers.NetworkConnectionReceiver
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.viewModels.FavoritesViewModel
import com.geekbrains.kotlin_lessons.viewModels.MovieViewModel

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var networkConnectionReceiver: NetworkConnectionReceiver

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Constants.BOOLEAN = false
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        networkConnectionReceiver = NetworkConnectionReceiver()
        when (networkConnectionReceiver.checkInternet(requireContext())) {
            false -> {
                requireView().findNavController().navigate(R.id.disconnectMovie)
            }
            true -> {
                favoritesViewModel = FavoritesViewModel()

                binding.adultContent.setOnCheckedChangeListener { _, _ ->
                    when (binding.adultContent.isChecked) {
                        true -> {
                            Constants.ADULT = true
                            favoritesViewModel.setPref(Constants.ADULT)
                        }
                        false -> {
                            Constants.ADULT = false
                            favoritesViewModel.setPref(Constants.ADULT)
                        }
                    }
                }
                Constants.ADULT = favoritesViewModel.getPref()
                when (Constants.ADULT) {
                    true -> binding.adultContent.isChecked = true
                    false -> binding.adultContent.isChecked = false
                }
                super.onViewCreated(view, savedInstanceState)
            }
        }
    }

}