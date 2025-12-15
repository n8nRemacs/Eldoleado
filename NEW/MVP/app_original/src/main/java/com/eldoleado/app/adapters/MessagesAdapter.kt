package com.eldoleado.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eldoleado.app.R
import com.eldoleado.app.api.Message
import com.eldoleado.app.data.database.entities.MessageEntity

// Унифицированная модель для сообщений
data class MessageItem(
    val id: String,
    val text: String,
    val senderType: String,
    val senderName: String?,
    val createdAt: String
)

// Extension для конвертации
fun Message.toMessageItem() = MessageItem(
    id = this.id ?: java.util.UUID.randomUUID().toString(),
    text = this.text.orEmpty(),
    senderType = this.sender_type.orEmpty(),
    senderName = this.sender_name,
    createdAt = this.created_at.orEmpty()
)

fun MessageEntity.toMessageItem() = MessageItem(
    id = this.id,
    text = this.text,
    senderType = this.senderType,
    senderName = this.senderName,
    createdAt = this.createdAt
)

class MessagesAdapter(initialMessages: List<Message> = emptyList()) :
    RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    private var messages: List<MessageItem> = initialMessages.map { it.toMessageItem() }

    companion object {
        private const val TYPE_CLIENT = 0
        private const val TYPE_OPERATOR = 1
        private const val TYPE_AGENT = 2
    }

    @Deprecated("Use updateMessagesFromEntities instead")
    fun updateMessages(newMessages: List<Message>) {
        messages = newMessages.map { it.toMessageItem() }
        notifyDataSetChanged()
    }

    fun updateMessagesFromEntities(newMessages: List<MessageEntity>) {
        val oldList = messages
        val newList = newMessages.map { it.toMessageItem() }

        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newList.size
            override fun areItemsTheSame(oldPos: Int, newPos: Int) =
                oldList[oldPos].id == newList[newPos].id
            override fun areContentsTheSame(oldPos: Int, newPos: Int) =
                oldList[oldPos] == newList[newPos]
        })

        messages = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return when (messages[position].senderType.lowercase()) {
            "client" -> TYPE_CLIENT
            "agent" -> TYPE_AGENT
            else -> TYPE_OPERATOR // operator и любые другие
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutId = when (viewType) {
            TYPE_CLIENT -> R.layout.item_message_client
            TYPE_AGENT -> R.layout.item_message_agent
            else -> R.layout.item_message_operator
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
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

        fun bind(message: MessageItem) {
            messageText.text = message.text
            // Показываем реальное имя если есть, иначе fallback
            senderType.text = message.senderName?.takeIf { it.isNotBlank() }
                ?: when (message.senderType.lowercase()) {
                    "client" -> "Клиент"
                    "operator" -> "Оператор"
                    "agent" -> "ИИ-агент"
                    else -> message.senderType.ifBlank { "—" }
                }
            timestamp.text = message.createdAt
        }
    }
}
