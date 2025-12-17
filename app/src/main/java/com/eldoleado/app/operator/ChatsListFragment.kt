package com.eldoleado.app.operator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eldoleado.app.R
import com.eldoleado.app.operator.models.Chat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Fragment displaying list of chats.
 */
class ChatsListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var connectionStatus: TextView
    private lateinit var adapter: ChatsAdapter

    private var onChatSelectedListener: ((Chat) -> Unit)? = null

    private val messageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                OperatorWebSocketService.BROADCAST_NEW_MESSAGE -> {
                    val messageJson = intent.getStringExtra(OperatorWebSocketService.EXTRA_MESSAGE)
                    messageJson?.let {
                        ChatsRepository.addIncomingMessage(it)
                    }
                }
                OperatorWebSocketService.BROADCAST_CONNECTION_STATE -> {
                    val connected = intent.getBooleanExtra(OperatorWebSocketService.EXTRA_CONNECTED, false)
                    updateConnectionStatus(connected)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chats_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_chats)
        emptyView = view.findViewById(R.id.text_empty)
        connectionStatus = view.findViewById(R.id.text_connection_status)

        adapter = ChatsAdapter { chat ->
            onChatSelectedListener?.invoke(chat)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        // Observe chats
        ChatsRepository.chats.observe(viewLifecycleOwner) { chats ->
            adapter.submitList(chats)
            emptyView.visibility = if (chats.isEmpty()) View.VISIBLE else View.GONE
            recyclerView.visibility = if (chats.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter().apply {
            addAction(OperatorWebSocketService.BROADCAST_NEW_MESSAGE)
            addAction(OperatorWebSocketService.BROADCAST_CONNECTION_STATE)
        }
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(messageReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(messageReceiver)
    }

    fun setOnChatSelectedListener(listener: (Chat) -> Unit) {
        onChatSelectedListener = listener
    }

    private fun updateConnectionStatus(connected: Boolean) {
        connectionStatus.text = if (connected) "Подключено" else "Отключено"
        connectionStatus.setBackgroundResource(
            if (connected) R.color.status_connected else R.color.status_disconnected
        )
    }

    companion object {
        fun newInstance() = ChatsListFragment()
    }
}

/**
 * Adapter for chats list.
 */
class ChatsAdapter(
    private val onChatClick: (Chat) -> Unit
) : ListAdapter<Chat, ChatsAdapter.ChatViewHolder>(ChatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view, onChatClick)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChatViewHolder(
        itemView: View,
        private val onChatClick: (Chat) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val channelIcon: TextView = itemView.findViewById(R.id.text_channel_icon)
        private val clientName: TextView = itemView.findViewById(R.id.text_client_name)
        private val lastMessage: TextView = itemView.findViewById(R.id.text_last_message)
        private val timestamp: TextView = itemView.findViewById(R.id.text_timestamp)
        private val unreadBadge: TextView = itemView.findViewById(R.id.text_unread_badge)

        private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        private val dateFormat = SimpleDateFormat("dd.MM", Locale.getDefault())

        fun bind(chat: Chat) {
            channelIcon.text = chat.channel.icon
            channelIcon.setBackgroundResource(getChannelBackground(chat.channel.id))

            clientName.text = chat.clientName
            lastMessage.text = chat.lastMessage

            // Format time
            val now = System.currentTimeMillis()
            val messageTime = chat.lastMessageTime
            val diff = now - messageTime

            timestamp.text = when {
                diff < 24 * 60 * 60 * 1000 -> timeFormat.format(Date(messageTime))
                else -> dateFormat.format(Date(messageTime))
            }

            // Unread badge
            if (chat.unreadCount > 0) {
                unreadBadge.visibility = View.VISIBLE
                unreadBadge.text = if (chat.unreadCount > 99) "99+" else chat.unreadCount.toString()
            } else {
                unreadBadge.visibility = View.GONE
            }

            itemView.setOnClickListener {
                onChatClick(chat)
            }
        }

        private fun getChannelBackground(channelId: String): Int {
            return when (channelId) {
                "telegram" -> R.drawable.bg_channel_telegram
                "whatsapp" -> R.drawable.bg_channel_whatsapp
                "avito" -> R.drawable.bg_channel_avito
                "max" -> R.drawable.bg_channel_max
                else -> R.drawable.bg_channel_default
            }
        }
    }

    class ChatDiffCallback : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.chatId == newItem.chatId
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }
    }
}
