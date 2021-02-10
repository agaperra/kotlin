package com.geekbrains.kotlin_lessons.adapters

import com.geekbrains.kotlin_lessons.models.Movie

interface OnItemViewClickListener {
    fun onItemClick(movie: Movie)
}