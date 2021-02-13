package com.geekbrains.kotlin_lessons.models

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(stringResource: Int, length: Int) =
        Snackbar.make(this, resources.getString(stringResource),length)