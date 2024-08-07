package com.example.chatappkotlin

import android.content.Context
import android.content.SharedPreferences

class ChatsDataPreference (context : Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("chats_data_prefs",Context.MODE_PRIVATE)

    fun saveData(jsonMessage: String){
        val editor = prefs.edit()
        editor.putString("jsonMessage",jsonMessage)
        editor.apply()
    }

    fun getDateTime(): String?{
        return prefs.getString("jsonMessage",null)
    }

    fun clearData(){
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}