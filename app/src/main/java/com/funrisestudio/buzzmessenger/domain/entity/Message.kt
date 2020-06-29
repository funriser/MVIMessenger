package com.funrisestudio.buzzmessenger.domain.entity

import java.util.*

data class Message(
    val contactId: Int,
    val text: String,
    val timestamp: Date,
    val isReceived: Boolean // whether a message is sent or received
)