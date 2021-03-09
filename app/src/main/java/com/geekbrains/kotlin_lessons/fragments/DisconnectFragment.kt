package com.geekbrains.kotlin_lessons.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.viewModels.DisconnectViewModel
import com.geekbrains.kotlin_lessons.viewModels.FavoritesViewModel

class DisconnectFragment : Fragment() {


    private lateinit var disconnectViewModel: DisconnectViewModel
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        disconnectViewModel = DisconnectViewModel()
        return inflater.inflate(R.layout.connection_lost, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.refresh)
        swipeRefreshLayout.setOnRefreshListener {
            refresh(swipeRefreshLayout)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun refresh(swipeRefreshLayout: SwipeRefreshLayout) {
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.postOnAnimationDelayed({

            requireView().findNavController().navigate(R.id.navigation_movie)
            swipeRefreshLayout.isRefreshing = false
        }, 2000)
    }


}