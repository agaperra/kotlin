package com.geekbrains.kotlin_lessons.interactors.string

import android.content.Context
import com.geekbrains.kotlin_lessons.R

class StringInteractorImpl(context: Context) : StringInteractor {
    override val textPopular: String = context.getString(R.string.popular)
    override val textLookNow: String = context.getString(R.string.looknow)
    override val textUpComing: String = context.getString(R.string.upcoming)
    override val textTop: String = context.getString(R.string.top)
    override val textSearch: String = context.getString(R.string.pictures)
    override val textNoOverview: String = context.getString(R.string.noOverview)
    override val textUnknownRuntime: String = context.getString(R.string.unknownRuntime)
    override val textHour: String = context.getString(R.string.h)
    override val textMinute: String = context.getString(R.string.min)
    override val textUnknown: String = context.getString(R.string.unknown)
    override val textActor: String = context.getString(R.string.actor)
}