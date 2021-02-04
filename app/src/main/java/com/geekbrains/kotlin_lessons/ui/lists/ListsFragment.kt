package com.geekbrains.kotlin_lessons.ui.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.kotlin_lessons.R

class ListsFragment : Fragment() {

    private lateinit var listsViewModel: ListsViewModel
    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listsViewModel =
            ViewModelProvider(this).get(ListsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_lists, container, false)
        textView= root.findViewById(R.id.text_lists)

        return root
    }


    override fun onStart() {
        super.onStart()
        listsViewModel.liveData.observe(this, { textView.text = it })
    }
}