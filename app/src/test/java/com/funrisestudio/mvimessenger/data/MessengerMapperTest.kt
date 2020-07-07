package com.funrisestudio.mvimessenger.data

import com.funrisestudio.mvimessenger.data.messages.local.MessengerMapper
import com.funrisestudio.mvimessenger.data.room.entity.ContactRow
import com.funrisestudio.mvimessenger.data.room.entity.MessageRow
import com.funrisestudio.mvimessenger.data.utils.TestDateProvider
import com.funrisestudio.mvimessenger.domain.entity.Contact
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class MessengerMapperTest {

    private lateinit var messengerMapper: MessengerMapper

    private val testDate = Date()
    private val testDateProvider = TestDateProvider(testDate)

    @Before
    fun setUp() {
        messengerMapper = MessengerMapper(testDateProvider)
    }

    @Test
    fun `should map contact entity to table item`() {
        val input = Contact(
            id = 0,
            name = "SomeName",
            avatar = 1
        )
        val expected = ContactRow(
            id = 0,
            name = "SomeName",
            avatar = 1
        )
        assertEquals(expected, messengerMapper.toContactRow(input))
    }

    @Test
    fun `should map contact and message text to message table item`() {
        val inputContact = Contact(
            id = 1,
            name = "SomeName",
            avatar = 1
        )
        val inputText = "SomeText"
        val expected = MessageRow(
            id = 0,
            senderId = 1,
            receiverId = USER_ID,
            message = "SomeText",
            timestamp = testDate,
            isRead = false
        )
        assertEquals(expected, messengerMapper.toMessageRow(inputContact, inputText))
    }

}