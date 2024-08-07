package com.example.chatappkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ChatsFragment : Fragment() {

    private lateinit var userDao: UserDao
    private lateinit var chatDao: ChatDao
    private lateinit var messageDao: MessageDao
    private lateinit var chatListAdapter: ChatListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            val database = (requireActivity().application as MyApplication).database
            userDao = database.userDao()
            chatDao= database.chatDao()
            messageDao= database.messageDao()

            val dataFetcher = FirebaseDataFetcher(userDao,chatDao,messageDao,requireContext())
            dataFetcher.fetchAndStoreData()




        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chats, container, false)

        recyclerView = view.findViewById(R.id.chats_fragment_recycler_view)

        chatListAdapter = ChatListAdapter(listOf())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = chatListAdapter

        loadChats()

        return view
    }

    private fun loadChats(){
        CoroutineScope(Dispatchers.IO).launch {
            val chats = chatDao.getAllChats()
            val chatWithLastMessages = chats.map { chat ->
                val lastMessage = messageDao.getLastMessage(chat.chatId)
                ChatWithLastMessage(chat, lastMessage)
            }
            withContext(Dispatchers.Main){
                chatListAdapter.updateChats(chatWithLastMessages)
            }
        }
    }
}