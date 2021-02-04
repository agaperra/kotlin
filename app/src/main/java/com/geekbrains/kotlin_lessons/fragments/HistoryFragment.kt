package com.geekbrains.kotlin_lessons.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.viewModels.HistoryViewModel
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)

        return inflater.inflate(R.layout.fragment_history, container, false)
    }


    override fun onStart() {
        super.onStart()
        historyViewModel.liveData.observe(this, { text_history.text = it })
    }
}