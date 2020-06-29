package com.funrisestudio.buzzmessenger.data.room.entity

import androidx.room.Embedded
import java.util.*

class DialogRow(
    @Embedded
    val contact: ContactRow,
    val lastMessage: String,
    val lastMessageDate: Date,
    val unreadCount: Int
)