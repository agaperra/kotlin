@file:Suppress("DEPRECATION")

package com.geekbrains.kotlin_lessons.activity

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.kotlin_lessons.databinding.ActivityMainBinding

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

import com.geekbrains.kotlin_lessons.R


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)

    }

}