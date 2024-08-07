package com.example.chatappkotlin

import android.content.Context
import android.content.SharedPreferences
import android.view.Display

class UserPreferences (context: Context) {
    private val  prefs: SharedPreferences = context.getSharedPreferences("User_Prefs",Context.MODE_PRIVATE)

    fun saveUserData(userName: String, displayName: String){
        val editor = prefs.edit()
        editor.putString("user_name",userName)
        editor.putString("display_name",displayName)
        editor.apply()
    }
    fun getUserId() : String?{
        return prefs.getString("user_name",null)
    }
    fun getUserName() : String?{
        return prefs.getString("display_name",null)
    }

    fun clearUserData() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

}