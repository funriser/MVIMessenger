package com.funrisestudio.buzzmessenger.data

import com.funrisestudio.buzzmessenger.R
import com.funrisestudio.buzzmessenger.domain.Contact
import com.funrisestudio.buzzmessenger.domain.entity.Message
import com.funrisestudio.buzzmessenger.ui.messenger.MessageViewData
import java.util.*

val contacts = listOf(
    Contact(
        id = 1,
        name = "McFly",
        avatar = R.drawable.avatar_mc_fly
    ),
    Contact(
        id = 2,
        name = "Dr Brown",
        avatar = R.drawable.avatar_mc_fly
    ),
    Contact(
        id = 3,
        name = "Biff",
        avatar = R.drawable.avatar_mc_fly
    )
)

val messages = listOf(
    "Hey", "Hello", "It's time", "Wazzup", "Howdy"
)

fun randomMessages(count: Int): List<MessageViewData> {
    val list = mutableListOf<MessageViewData>()
    for (i in 0 until count) {
        list.add(
            MessageViewData(
                message = Message(
                    contactId = 0,
                    text = messages.random(),
                    timestamp = Date(),
                    isReceived = true
                ),
                formattedDate = "$i:00"
            )
        )
    }
    return list
}