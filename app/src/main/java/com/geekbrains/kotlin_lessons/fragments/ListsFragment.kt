package com.geekbrains.kotlin_lessons.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.viewModels.ListsViewModel
import kotlinx.android.synthetic.main.fragment_lists.*

class ListsFragment : Fragment() {

    private lateinit var listsViewModel: ListsViewModel
    //private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listsViewModel =
            ViewModelProvider(this).get(ListsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_lists, container, false)
    }


    override fun onStart() {
        super.onStart()
        listsViewModel.liveData.observe(this, { text_lists.text = it })
    }
}