package com.example.chatappkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatListAdapter(private var chatList: List<ChatWithLastMessage>) :
    RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chats_fragment_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatWithLastMessage = chatList[position]
        holder.bind(chatWithLastMessage)
    }

    fun updateChats(newChatList: List<ChatWithLastMessage>) {
        chatList = newChatList
        notifyDataSetChanged()
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(chatWithLastMessage: ChatWithLastMessage) {
            val chatNameTextView : TextView = itemView.findViewById(R.id.chatNameTextView)
            val lastMessageTextView : TextView = itemView.findViewById(R.id.lastMessageTextView)
            val timestampTextView : TextView = itemView.findViewById(R.id.timestampTextView)

            chatNameTextView.text = chatWithLastMessage.chat.chatName
            lastMessageTextView.text = chatWithLastMessage.lastMessage?.text ?: "No messages"
            timestampTextView.text = chatWithLastMessage.lastMessage?.timestamp?.let { formatDate(it) } ?: ""
        }

        private fun formatDate(timestamp: Long): String {
            val sdf = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss", java.util.Locale.getDefault())
            val date = java.util.Date(timestamp)
            return sdf.format(date)
        }
    }
}