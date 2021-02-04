package com.geekbrains.kotlin_lessons.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.kotlin_lessons.*
import com.geekbrains.kotlin_lessons.activity.MainActivity
import com.geekbrains.kotlin_lessons.sharedPeferences.CheckPreferences
import com.geekbrains.kotlin_lessons.sharedPeferences.SharedPreferencesManager
import com.geekbrains.kotlin_lessons.viewModels.MovieViewModel

class MovieFragment : Fragment() {
    companion object {
        lateinit var sPrefs: SharedPreferencesManager
    }

    private lateinit var textView: TextView
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movieViewModel =
            ViewModelProvider(this).get(MovieViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_movie, container, false)
        textView= root.findViewById(R.id.text_movie)

        sPrefs = MainActivity.sPrefs
        val k:Int =resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        textView.setOnClickListener {
            if (k == Configuration.UI_MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sPrefs.storeString(Constants.TAGS.THEME_TAG.toString(), Constants.THEME.THEME_DARK.toString())
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sPrefs.storeString(Constants.TAGS.THEME_TAG.toString(), Constants.THEME.THEME_LIGHT.toString())
            }
        }

        CheckPreferences.checkTheme(sPrefs)
        return root
    }

    override fun onStart() {
        super.onStart()
        movieViewModel.liveData.observe(this, { textView.text = it })
    }



}


