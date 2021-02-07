package com.geekbrains.kotlin_lessons.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.viewModels.SearchViewModel


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
                ViewModelProvider(this).get(SearchViewModel::class.java)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onStart() {
        super.onStart()
     //  historyViewModel.liveData.observe(this, { text_history.text = it })
    }
}