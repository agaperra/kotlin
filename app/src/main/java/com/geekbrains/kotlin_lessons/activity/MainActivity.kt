@file:Suppress("DEPRECATION")

package com.geekbrains.kotlin_lessons.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var backPress: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigation()
    }

    private fun setNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)
    }


    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount != 0 && supportFragmentManager.findFragmentByTag(
                supportFragmentManager.getBackStackEntryAt(
                    supportFragmentManager.backStackEntryCount - 1
                ).name
            ) == supportFragmentManager.findFragmentByTag("info")
        ) {
            supportFragmentManager.popBackStack()
        } else {
            if (backPress + 2000 > System.currentTimeMillis()) {
                finish()
            } else {
                val snackbar =
                    Snackbar.make(binding.root, getString(R.string.try_exit), Snackbar.LENGTH_LONG)
                @SuppressLint("InflateParams") val customSnackView: View =
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
}
