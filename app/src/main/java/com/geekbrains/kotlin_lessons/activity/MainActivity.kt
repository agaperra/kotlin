package com.geekbrains.kotlin_lessons.activity

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.utils.SharedPreferencesManager
import com.geekbrains.kotlin_lessons.databinding.ActivityMainBinding
import com.geekbrains.kotlin_lessons.App
import com.geekbrains.kotlin_lessons.utils.Constants
import com.geekbrains.kotlin_lessons.utils.Variables
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var backPress: Long = 0
    private var TIME_EXIT: Int = 2000
    companion object {
        var preferencesManager: SharedPreferencesManager? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencesManager = SharedPreferencesManager(this)
        getThemeFromSP()
        setNavigation()
    }


    private fun setNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)
    }


    override fun onBackPressed() {

        if (Variables.BOOLEAN) {
            super.onBackPressed()
        } else {
            if (backPress + TIME_EXIT > System.currentTimeMillis()) {
                finish()
            } else {
                val snackbar =
                    Snackbar.make(binding.root, getString(R.string.try_exit), Snackbar.LENGTH_LONG)

                @SuppressLint("InflateParams")
                val customSnackView: View =
                    layoutInflater.inflate(R.layout.rounded, null)
                snackbar.view.setBackgroundColor(Color.TRANSPARENT)
                val snackbarLayout = snackbar.view as SnackbarLayout

                snackbarLayout.setPadding(20, 20, 20, 20)
                snackbarLayout.addView(customSnackView, 0)
                snackbar.show()
            }
            backPress = System.currentTimeMillis()
        }
    }

    private fun getThemeFromSP() {
        when (preferencesManager?.retrieveInt(Constants.TAG_THEME, Constants.THEME_DARK)) {
            0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
