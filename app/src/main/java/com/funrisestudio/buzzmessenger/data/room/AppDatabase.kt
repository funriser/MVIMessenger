package com.funrisestudio.buzzmessenger.data.room

import android.content.Context
import androidx.room.*
import com.funrisestudio.buzzmessenger.data.room.entity.DialogRow
import com.funrisestudio.buzzmessenger.data.room.entity.MessageRow
import com.funrisestudio.buzzmessenger.data.room.entity.SenderRow
import kotlinx.coroutines.flow.Flow
import java.util.*

@Database(entities = [SenderRow::class, MessageRow::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        fun build(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "msg_db").build()
        }
    }
    abstract fun messagesDao(): MessagesDao
}

@Dao
interface MessagesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSender(senderRow: SenderRow)

    @Insert
    suspend fun insertMessage(messageRow: MessageRow)

    @Transaction
    suspend fun insertNewMessage(senderRow: SenderRow, messageRow: MessageRow) {
        insertSender(senderRow)
        insertMessage(messageRow)
    }

    @Query("SELECT s.id, s.name, s.avatar, m1.message as lastMessage, m1.timestamp as lastMessageDate FROM senders s JOIN messages m1 ON (s.id = m1.senderId) LEFT OUTER JOIN messages m2 ON (s.id = m2.senderId AND  (m1.timestamp < m2.timestamp OR (m1.timestamp = m2.timestamp AND m1.id < m2.id))) WHERE m2.id IS NULL;")
    fun getDialogs(): Flow<List<DialogRow>>

}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}