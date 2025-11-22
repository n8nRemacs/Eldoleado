package com.batterycrm.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batterycrm.app.R
import com.batterycrm.app.api.Message

class MessagesAdapter(private val messages: List<Message>) : 
    RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }
    
    override fun getItemCount() = messages.size
    
    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.messageText)
        private val senderType: TextView = itemView.findViewById(R.id.senderType)
        private val timestamp: TextView = itemView.findViewById(R.id.timestamp)
        
        fun bind(message: Message) {
            messageText.text = message.text.orEmpty()
            senderType.text = when (message.sender_type) {
                "client" -> "Клиент"
                "operator" -> "Оператор"
                else -> message.sender_type ?: "—"
            }
            timestamp.text = message.created_at ?: ""
        }
    }
}
