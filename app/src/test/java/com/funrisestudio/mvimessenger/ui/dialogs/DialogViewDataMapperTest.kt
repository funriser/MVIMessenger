package com.funrisestudio.mvimessenger.ui.dialogs

import com.funrisestudio.mvimessenger.domain.dialogs.Dialog
import com.funrisestudio.mvimessenger.domain.dialogs.MessagePreview
import com.funrisestudio.mvimessenger.domain.entity.Contact
import com.funrisestudio.mvimessenger.ui.utils.DateFormat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class DialogViewDataMapperTest {

    private val dateFormat: DateFormat = mock()

    private lateinit var dgViewDataMapper: DialogViewDataMapper

    @Before
    fun setUp() {
        dgViewDataMapper = DialogViewDataMapper(dateFormat)
    }

    @Test
    fun `should map entity to view data`() {
        val msgDate = Date()
        val msgDateStr = "01.01.2020"
        val testInput = listOf(
            Dialog(
                contact = Contact(
                    id = 1,
                    name = "SomeName",
                    avatar = 1
                ),
                lastMessage = MessagePreview(
                    text = "SomeText",
                    date = msgDate
                ),
                unreadCount = 1
            )
        )
        whenever(dateFormat.timeFormat(msgDate))
            .thenReturn(msgDateStr)

        val actualResult = dgViewDataMapper.getDialogViewDataList(testInput)

        val expected = listOf(
            DialogViewData(
                dialog = testInput[0],
                formattedDate = msgDateStr
            )
        )

        assertEquals(expected, actualResult)
    }

}