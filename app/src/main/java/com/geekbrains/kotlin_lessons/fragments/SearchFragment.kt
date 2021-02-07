package com.geekbrains.kotlin_lessons.fragments

import android.graphics.Color
import android.icu.text.CaseMap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.databinding.FragmentSearchBinding
import com.geekbrains.kotlin_lessons.viewModels.SearchViewModel


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        setUpSearchView()
        doInitialization()
        return binding.root
    }

    private fun doInitialization() {
        searchViewModel =
                ViewModelProvider(this).get(SearchViewModel::class.java)

    }

    private fun setUpSearchView() {
        val searchView = binding.searchView
        var id = searchView.context
                .resources
                .getIdentifier("android:id/search_src_text", null, null)
        val textView = searchView.findViewById<View>(id) as TextView
        textView.setTextColor(Color.parseColor("#D1A874"))
        textView.setHintTextColor(Color.parseColor("#D8C5AE"));

        id = searchView.context
                .resources
                .getIdentifier("android:id/search_close_btn", null, null)
        var imageView = searchView.findViewById<View>(id) as ImageView
        imageView.setImageResource(R.drawable.ic_baseline_close_24)

        id = searchView.context
                .resources
                .getIdentifier("android:id/search_button", null, null)
        imageView = searchView.findViewById<View>(id) as ImageView
        imageView.setImageResource(R.drawable.searcview_icon)

//        id = searchView.context
//                .resources
//                .getIdentifier("android:id/search_title", null, null)
//        searchView.clearFocus();
//        val text=searchView.findViewById<View>(id) as SearchView.SearchAutoComplete
//        text.setText(R.string.edit_search)
    }

}