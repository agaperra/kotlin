package com.geekbrains.kotlin_lessons.room.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WatchLater(
    @PrimaryKey
    val id: Int
)
