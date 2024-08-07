package com.example.chatappkotlin

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "chats")
data class Chat(
    @PrimaryKey val chatId: String,
    val chatName: String
    )

@Dao
interface ChatDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat : Chat)

    @Query("SELECT * FROM chats")
    suspend fun getAllChats() : List<Chat>
}
