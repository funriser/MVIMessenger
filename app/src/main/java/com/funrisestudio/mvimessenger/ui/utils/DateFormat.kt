package com.funrisestudio.mvimessenger.ui.utils

import com.funrisestudio.mvimessenger.utils.Open
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Open
class DateFormat @Inject constructor() {

    private var sdfTime = SimpleDateFormat(PATTERN_TIME, Locale.getDefault())

    @Open
    fun timeFormat(date: Date): String {
        return sdfTime.format(date)
    }

    companion object {
        private const val PATTERN_TIME = "HH:mm"
    }

}