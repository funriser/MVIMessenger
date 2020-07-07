package com.funrisestudio.mvimessenger.data

import com.funrisestudio.mvimessenger.data.conversation.local.ConversationMapper
import com.funrisestudio.mvimessenger.data.room.entity.MessageRow
import com.funrisestudio.mvimessenger.domain.entity.Message
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class ConversationMapperTest {

    private lateinit var conversationMapper: ConversationMapper

    @Before
    fun setUp() {
        conversationMapper = ConversationMapper()
    }

    @Test
    fun `should map messages table items to entity if user is receiver`() {
        val msgDate = Date()
        val input = listOf(
            MessageRow(
                id = 0,
                senderId = 1,
                receiverId = USER_ID,
                message = "SomeMessage",
                timestamp = msgDate,
                isRead = false
            )
        )
        val expected = listOf(
            Message(
                contactId = 1,
                text = "SomeMessage",
                timestamp = msgDate,
                isReceived = true
            )
        )
        assertEquals(expected, conversationMapper.getMessages(input))
    }

    @Test
    fun `should map messages table items to entity if user is sender`() {
        val msgDate = Date()
        val input = listOf(
            MessageRow(
                id = 0,
                senderId = USER_ID,
                receiverId = 1,
                message = "SomeMessage",
                timestamp = msgDate,
                isRead = false
            )
        )
        val expected = listOf(
            Message(
                contactId = 1,
                text = "SomeMessage",
                timestamp = msgDate,
                isReceived = false
            )
        )
        assertEquals(expected, conversationMapper.getMessages(input))
    }

}