package com.geekbrains.kotlin_lessons.adapters

import com.geekbrains.kotlin_lessons.models.Actor

interface OnActorViewClickListener {
    fun onItemClick(actor: Actor)
}