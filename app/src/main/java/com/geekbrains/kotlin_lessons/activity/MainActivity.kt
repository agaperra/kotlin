package com.geekbrains.kotlin_lessons.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.geekbrains.kotlin_lessons.R
import com.geekbrains.kotlin_lessons.databinding.ActivityMainBinding
import com.geekbrains.kotlin_lessons.utils.SnackBarMaker
import com.geekbrains.kotlin_lessons.utils.Variables
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var backPress: Long = 0
    private var exitPress: Int = 2000

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

        if (Variables.BOOLEAN) {
            super.onBackPressed()
        } else {
            if (backPress + exitPress > System.currentTimeMillis()) {
                finish()
            } else {
                val snackbar =
                    Snackbar.make(binding.root, getString(R.string.try_exit), Snackbar.LENGTH_LONG)
                SnackBarMaker.createAndShowSnackBar(layoutInflater, snackbar)
            }
            backPress = System.currentTimeMillis()
        }
    }
}
