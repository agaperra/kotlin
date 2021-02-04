package com.geekbrains.kotlin_lessons.ui.movie

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.kotlin_lessons.*

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
                sPrefs.storeInt(Constants.THEME_TAG, Constants.THEME_DARK)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sPrefs.storeInt(Constants.THEME_TAG, Constants.THEME_LIGHT)
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


