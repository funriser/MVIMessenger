package com.funrisestudio.mvimessenger.data

import com.funrisestudio.mvimessenger.data.dialogs.local.DialogsMapper
import com.funrisestudio.mvimessenger.data.room.entity.ContactRow
import com.funrisestudio.mvimessenger.data.room.entity.DialogRow
import com.funrisestudio.mvimessenger.domain.dialogs.Dialog
import com.funrisestudio.mvimessenger.domain.dialogs.MessagePreview
import com.funrisestudio.mvimessenger.domain.entity.Contact
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class DialogsMapperTest {

    private lateinit var dialogsMapper: DialogsMapper

    @Before
    fun setUp() {
        dialogsMapper = DialogsMapper()
    }

    @Test
    fun `should map dialog table item to entity`() {
        val lastMsgDate = Date()
        val input = listOf(
            DialogRow(
                contact = ContactRow(
                    id = 42,
                    name = "SomeName",
                    avatar = 1
                ),
                lastMessage = "SomeMessage",
                lastMessageDate = lastMsgDate,
                unreadCount = 1
            )
        )
        val expected = listOf(
            Dialog(
                contact = Contact(
                    id = 42,
                    name = "SomeName",
                    avatar = 1
                ),
                lastMessage = MessagePreview(
                    text = "SomeMessage",
                    date = lastMsgDate
                ),
                unreadCount = 1
            )
        )
        assertEquals(expected, dialogsMapper.getDialogs(input))
    }

}