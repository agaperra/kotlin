package com.geekbrains.kotlin_lessons.room.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey val id: Int,
    val overview: String?,
    val poster_path: String?,
    val release_date: String,
    val title: String
)