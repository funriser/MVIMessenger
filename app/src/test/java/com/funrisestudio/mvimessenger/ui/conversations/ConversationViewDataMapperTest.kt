package com.funrisestudio.mvimessenger.ui.conversations

import com.funrisestudio.mvimessenger.domain.entity.Message
import com.funrisestudio.mvimessenger.ui.conversation.ConversationViewDataMapper
import com.funrisestudio.mvimessenger.ui.conversation.MessageViewData
import com.funrisestudio.mvimessenger.ui.utils.DateFormat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class ConversationViewDataMapperTest {

    private val dateFormat: DateFormat = mock()

    private lateinit var conViewDataMapper: ConversationViewDataMapper

    @Before
    fun setUp() {
        conViewDataMapper = ConversationViewDataMapper(dateFormat)
    }

    @Test
    fun `should map entity to view data`() {
        val msgDate = Date()
        val msgDateStr = "01.01.2020"
        val testInput = listOf(
            Message(
                contactId = 1,
                text = "SomeText",
                timestamp = msgDate,
                isReceived = false
            )
        )
        whenever(dateFormat.timeFormat(msgDate))
            .thenReturn(msgDateStr)

        val actualResult = conViewDataMapper.getMessageViewDataList(testInput)

        val expected = listOf(
            MessageViewData(
                message = testInput[0],
                formattedDate = msgDateStr
            )
        )

        assertEquals(expected, actualResult)
    }

}