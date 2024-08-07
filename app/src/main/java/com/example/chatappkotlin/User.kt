package com.example.chatappkotlin

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "users")
data class User(
    @PrimaryKey @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "displayName") val displayName: String
)

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long // Return type for insert

    @Query("SELECT * FROM users WHERE username = :userName")
    suspend fun getUser(userName: String): User?// Return type for query
}
