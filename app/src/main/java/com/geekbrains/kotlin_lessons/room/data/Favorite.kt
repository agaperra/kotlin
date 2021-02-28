package com.geekbrains.kotlin_lessons.room.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey
    val id: Int
)