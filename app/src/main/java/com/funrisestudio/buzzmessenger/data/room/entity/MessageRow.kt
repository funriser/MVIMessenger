package com.funrisestudio.buzzmessenger.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.funrisestudio.buzzmessenger.data.USER_ID
import java.util.*

@Entity(
    tableName = "messages"
)
data class MessageRow(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val senderId: Int,
    val receiverId: Int = USER_ID,
    val message: String,
    val timestamp: Date
)