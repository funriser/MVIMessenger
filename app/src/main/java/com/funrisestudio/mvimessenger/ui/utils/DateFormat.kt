package com.funrisestudio.mvimessenger.ui.utils

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateFormat @Inject constructor() {

    var sdfTime = SimpleDateFormat(PATTERN_TIME, Locale.getDefault())

    fun timeFormat(date: Date): String {
        return sdfTime.format(date)
    }

    companion object {
        private const val PATTERN_TIME = "HH:mm"
    }

}