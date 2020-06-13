package com.funrisestudio.buzzmessenger.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "messages",
    foreignKeys = [ForeignKey(
        entity = SenderRow::class,
        parentColumns = ["id"],
        childColumns = ["senderId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MessageRow(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val senderId: Int,
    val message: String
)