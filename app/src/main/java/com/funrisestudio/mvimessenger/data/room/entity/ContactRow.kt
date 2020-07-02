package com.funrisestudio.mvimessenger.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "senders")
class ContactRow(
    @PrimaryKey val id: Int,
    val name: String,
    val avatar: Int
)