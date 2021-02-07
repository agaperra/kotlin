package com.geekbrains.kotlin_lessons.interactors.string

import android.content.Context
import com.geekbrains.kotlin_lessons.R

class StringInteractorImpl(context: Context) : StringInteractor {
    override val textPopular: String=context.getString(R.string.popular)
    override val textLookNow: String=context.getString(R.string.looknow)
    override val textUpComing: String =context.getString(R.string.upcoming)
    override val textTop: String = context.getString(R.string.top)
    override val textSearch: String =context.getString(R.string.pictures)
}