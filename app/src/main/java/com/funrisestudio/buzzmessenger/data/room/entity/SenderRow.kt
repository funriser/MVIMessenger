package com.funrisestudio.buzzmessenger.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "senders")
class SenderRow(
    @PrimaryKey val id: Int,
    val name: String,
    val avatar: Int
)