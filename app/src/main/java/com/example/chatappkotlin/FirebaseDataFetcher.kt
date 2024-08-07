package com.example.chatappkotlin

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirebaseDataFetcher(
    private val userDao: UserDao,
    private val chatDao: ChatDao,
    private val messageDao: MessageDao,
    private val context: Context
) {
    private val database = FirebaseDatabase.getInstance()

    fun fetchAndStoreData() {
        fetchUsers()
        fetchChats()
        fetchMessages()
    }




    private fun fetchUsers() {
        val userRef = database.getReference("Users")
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                CoroutineScope(Dispatchers.IO).launch {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        user?.let { userDao.insertUser(it) }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                CallToast.show(context, "User Fetch Error : " + error.message)
            }

        })
    }

    private fun fetchChats() {
        val chatRef = database.getReference("Chats")
        chatRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                CoroutineScope(Dispatchers.IO).launch {
                    for(chatSnapshot in snapshot.children) {
                        val chat = chatSnapshot.getValue(Chat::class.java)
                        chat?.let { chatDao.insertChat(it) }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                CallToast.show(context, "User Fetch Error : " + error.message)
            }

        })
    }

    private fun fetchMessages() {
        val messagesRef = database.getReference("messages")
        messagesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                CoroutineScope(Dispatchers.IO).launch {
                    for (messageSnapshot in snapshot.children) {
                        val message = messageSnapshot.getValue(Message::class.java)
                        message?.let { messageDao.insertMessage(it) }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                CallToast.show(context, "User Fetch Error : " + error.message)
            }
        })
    }
}



