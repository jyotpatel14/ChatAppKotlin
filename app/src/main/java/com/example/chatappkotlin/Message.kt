package com.example.chatappkotlin

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.google.firebase.Timestamp

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey val msgId: String,
    val chatId: String,
    val senderId: String,
    val text: String,
    val timestamp: Long
)

@Dao
interface MessageDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)

    @Query("SELECT * FROM messages WHERE chatId = :chatId ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastMessage(chatId: String): Message?

    @Query("SELECT * FROM messages WHERE chatId = :chatId ORDER BY timestamp DESC LIMIT 100")
    suspend fun getAllMessages(chatId: String): List<Message>
}

