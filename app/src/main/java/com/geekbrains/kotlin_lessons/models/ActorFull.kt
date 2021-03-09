package com.geekbrains.kotlin_lessons.models

data class ActorFull(
        val also_known_as: ArrayList<String>,
        val biography: String,
        val birthday: String,
        val name: String,
        val place_of_birth: String,
        val profile_path: String?
)