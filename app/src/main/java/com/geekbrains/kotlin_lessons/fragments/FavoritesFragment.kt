package com.geekbrains.kotlin_lessons.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.viewModels.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Constants.boolean = false
        favoritesViewModel =
                ViewModelProvider(this).get(FavoritesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

}