package com.example.chatappkotlin

import android.content.Context
import android.content.SharedPreferences
import android.view.Display

class UserPreferences (context: Context) {
    private val  prefs: SharedPreferences = context.getSharedPreferences("User_Prefs",Context.MODE_PRIVATE)

    fun saveUserData(userId: String, userName: String){
        val editor = prefs.edit()
        editor.putString("user_id",userId)
        editor.putString("user_name",userName)
        editor.apply()

    }
    fun getUserId() : String?{
        return prefs.getString("user_id",null)
    }
    fun getUserName() : String?{
        return prefs.getString("user_name",null)
    }

    fun clearUserData() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

}