package com.example.chatappkotlin

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class,Chat::class,Message::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun chatDao() : ChatDao
    abstract fun messageDao() : MessageDao

}