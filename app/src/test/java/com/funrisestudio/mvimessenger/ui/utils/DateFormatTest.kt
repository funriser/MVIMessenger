package com.funrisestudio.mvimessenger.ui.utils

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat

class DateFormatTest {

    private lateinit var dateFormat: DateFormat

    @Before
    fun setUp() {
        dateFormat = DateFormat()
    }

    @Test
    fun `should convert date obj to min and secs 1`() {
        val testDate = SimpleDateFormat("dd/MM/YYY HH:mm").run {
            parse("01/01/2020 01:30")
        }?:throw IllegalStateException("Date should not be null")
        assertEquals("01:30", dateFormat.timeFormat(testDate))
    }

    @Test
    fun `should convert date obj to min and secs 2`() {
        val testDate = SimpleDateFormat("dd/MM/YYY HH:mm").run {
            parse("01/01/2020 00:00")
        }?:throw IllegalStateException("Date should not be null")
        assertEquals("00:00", dateFormat.timeFormat(testDate))
    }

    @Test
    fun `should convert date obj to min and secs 3`() {
        val testDate = SimpleDateFormat("dd/MM/YYY HH:mm").run {
            parse("01/01/2020 23:59")
        }?:throw IllegalStateException("Date should not be null")
        assertEquals("23:59", dateFormat.timeFormat(testDate))
    }

}