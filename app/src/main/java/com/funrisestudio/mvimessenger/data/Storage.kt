package com.funrisestudio.mvimessenger.data

import com.funrisestudio.mvimessenger.R
import com.funrisestudio.mvimessenger.domain.entity.Contact
import com.funrisestudio.mvimessenger.domain.entity.Message
import com.funrisestudio.mvimessenger.ui.conversation.MessageViewData
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
    "Hey", "Hello", "It's time", "Wazzup", "Howdy",
    "How are you doing?", "What time is now?", "Call me maybe",
    "It's me", "Send memes", "I'll be there by 10", "Let's meet"
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