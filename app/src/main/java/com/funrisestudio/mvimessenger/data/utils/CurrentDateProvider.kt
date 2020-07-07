package com.funrisestudio.mvimessenger.data.utils

import java.util.*
import javax.inject.Inject

class CurrentDateProvider @Inject constructor(): DateProvider {
    override fun getDate(): Date {
        return Date()
    }
}