package com.funrisestudio.buzzmessenger.data.room

import android.content.Context
import androidx.room.*
import com.funrisestudio.buzzmessenger.data.room.entity.MessageRow
import com.funrisestudio.buzzmessenger.data.room.entity.SenderRow

@Database(entities = [SenderRow::class, MessageRow::class], version = 1)
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

}