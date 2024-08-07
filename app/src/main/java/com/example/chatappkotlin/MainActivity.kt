package com.example.chatappkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var userPreferences: UserPreferences
    private lateinit var database: FirebaseDatabase

    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userId = checkUserPreferenceExist()
        if(userId != null){
            database = FirebaseDatabase.getInstance()
            loadUserChats(userId)
        }

        //all view setup can be done after setting up the "main"
        val mainView = findViewById<View>(R.id.main_main)
        notchBarSetup(mainView)
        fragmentContainerSetup(savedInstanceState)








    }

    private fun fragmentContainerSetup(savedInstanceState: Bundle?){
        fragmentContainerView = findViewById(R.id.fragmentContainerView)
        bottomNavigationView = findViewById(R.id.bottom_nav)

        if (savedInstanceState == null) {
            setFragment(ChatsFragment())
            bottomNavigationView.selectedItemId = R.id.chats
        }

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.chats->{
                    setFragment(ChatsFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.search->{
                    setFragment(SearchFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.profile->{
                    setFragment(ProfileFragment())
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,fragment).commit()
    }

    private fun loadUserChats(userId:String){
        val userChatRef = database.getReference("users/$userId/chat_ids")
        userChatRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(chatSnapshot in snapshot.children){
                    val chatId = chatSnapshot.key
                    if (chatId != null){
                        loadChatMessages(chatId)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"Failed to Load Chats", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loadChatMessages(chatId: String?) {
        val messagesRef = database.getReference("chats/$chatId/messages")
        messagesRef.limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(messageSnapshot in snapshot.children){
//                    val message = messageSnapshot.getValue(Message::class.java)
//                    if(message != null){
//                        message.add(message)
//                    }
                    Toast.makeText(applicationContext,"",Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"Failed to Load Messages", Toast.LENGTH_LONG).show()
            }

        })
    }


    private fun checkUserPreferenceExist(): String? {
        userPreferences = UserPreferences(this)
        if(userPreferences.getUserId() == null){
            val intent = Intent(this@MainActivity, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
            setContentView(R.layout.activity_main)
        }
        return userPreferences.getUserId()
    }

    private fun notchBarSetup(mainView : View){
        ViewCompat.setOnApplyWindowInsetsListener(mainView){ view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = systemBarsInsets.top
            }
            // Return the insets so they can be consumed by other views
            insets
        }
    }
}