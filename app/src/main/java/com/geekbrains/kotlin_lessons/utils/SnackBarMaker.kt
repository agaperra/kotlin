package com.geekbrains.kotlin_lessons.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import com.geekbrains.kotlin_lessons.R
import com.google.android.material.snackbar.Snackbar

class SnackBarMaker {
    companion object {

        fun createAndShowSnackBar(layoutInflater: LayoutInflater, snackbar: Snackbar) {
            @SuppressLint("InflateParams")
            val customSnackView: View =
                layoutInflater.inflate(R.layout.rounded, null)
            snackbar.view.setBackgroundColor(Color.TRANSPARENT)
            val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

            snackbarLayout.setPadding(20, 20, 20, 20)
            snackbarLayout.addView(customSnackView, 0)
            snackbar.show()
        }
    }
}