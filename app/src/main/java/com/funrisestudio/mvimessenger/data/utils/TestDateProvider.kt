package com.funrisestudio.mvimessenger.data.utils

import java.util.*

class TestDateProvider(private val date: Date): DateProvider {
    override fun getDate(): Date {
        return date
    }
}