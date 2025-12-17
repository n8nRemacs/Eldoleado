package com.eldoleado.app.operator

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eldoleado.app.R
import com.eldoleado.app.operator.models.Chat
import com.eldoleado.app.operator.models.ChatMessage
import java.text.SimpleDateFormat
import java.util.*

/**
 * Fragment for displaying chat messages and sending new messages.
 */
class ChatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var inputField: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var headerTitle: TextView
    private lateinit var headerChannel: TextView
    private lateinit var adapter: MessagesAdapter

    private var chatId: String = ""
    private var chat: Chat? = null
    private var wsService: OperatorWebSocketService? = null
    private var serviceBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as OperatorWebSocketService.LocalBinder
            wsService = binder.getService()
            serviceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            wsService = null
            serviceBound = false
        }
    }

    private val messageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                OperatorWebSocketService.BROADCAST_NEW_MESSAGE -> {
                    val messageJson = intent.getStringExtra(OperatorWebSocketService.EXTRA_MESSAGE)
                    messageJson?.let {
                        ChatsRepository.addIncomingMessage(it)
                    }
                }
                OperatorWebSocketService.BROADCAST_SHOW_DRAFT -> {
                    val draftJson = intent.getStringExtra(OperatorWebSocketService.EXTRA_MESSAGE)
                    draftJson?.let {
                        ChatsRepository.setDraft(it)
                        showDraftApprovalDialog()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatId = arguments?.getString(ARG_CHAT_ID) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_messages)
        inputField = view.findViewById(R.id.edit_message)
        sendButton = view.findViewById(R.id.btn_send)
        headerTitle = view.findViewById(R.id.text_header_title)
        headerChannel = view.findViewById(R.id.text_header_channel)

        adapter = MessagesAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context).apply {
            stackFromEnd = true
        }
        recyclerView.adapter = adapter

        // Get chat info
        chat = ChatsRepository.getChat(chatId)
        chat?.let {
            headerTitle.text = it.clientName
            headerChannel.text = it.channel.displayName
        }

        // Mark as read
        ChatsRepository.markChatAsRead(chatId)

        // Observe messages
        ChatsRepository.getMessagesLiveData(chatId).observe(viewLifecycleOwner) { messages ->
            adapter.submitList(messages)
            if (messages.isNotEmpty()) {
                recyclerView.scrollToPosition(messages.size - 1)
            }
        }

        // Send button
        sendButton.setOnClickListener {
            sendMessage()
        }

        // Back button
        view.findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onStart() {
        super.onStart()
        // Bind to WebSocket service
        Intent(context, OperatorWebSocketService::class.java).also { intent ->
            context?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        if (serviceBound) {
            context?.unbindService(serviceConnection)
            serviceBound = false
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter().apply {
            addAction(OperatorWebSocketService.BROADCAST_NEW_MESSAGE)
            addAction(OperatorWebSocketService.BROADCAST_SHOW_DRAFT)
        }
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(messageReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(messageReceiver)
    }

    private fun sendMessage() {
        val text = inputField.text.toString().trim()
        if (text.isEmpty()) return

        val chat = this.chat ?: return

        // Send via WebSocket
        wsService?.sendMessage(
            chatId = chatId,
            channel = chat.channel.id,
            text = text,
            serverId = chat.serverId
        )

        // Add to local messages (will be normalized and returned as draft)
        ChatsRepository.addOutgoingMessage(
            chatId = chatId,
            text = text,
            channel = chat.channel.id,
            serverId = chat.serverId
        )

        inputField.text.clear()
    }

    private fun showDraftApprovalDialog() {
        val draft = ChatsRepository.currentDraft.value ?: return
        if (draft.chatId != chatId) return

        DraftApprovalDialog.newInstance(
            draftId = draft.id,
            chatId = draft.chatId,
            channel = draft.channel.id,
            originalText = draft.originalText,
            normalizedText = draft.normalizedText,
            serverId = draft.serverId
        ).show(childFragmentManager, "draft_approval")
    }

    companion object {
        private const val ARG_CHAT_ID = "chat_id"

        fun newInstance(chatId: String): ChatFragment {
            return ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CHAT_ID, chatId)
                }
            }
        }
    }
}

/**
 * Adapter for messages in chat.
 */
class MessagesAdapter : ListAdapter<ChatMessage, MessagesAdapter.MessageViewHolder>(MessageDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isIncoming) VIEW_TYPE_INCOMING else VIEW_TYPE_OUTGOING
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutId = if (viewType == VIEW_TYPE_INCOMING) {
            R.layout.item_message_incoming
        } else {
            R.layout.item_message_outgoing
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.text_message)
        private val timestamp: TextView = itemView.findViewById(R.id.text_timestamp)
        private val senderName: TextView? = itemView.findViewById(R.id.text_sender_name)

        private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        fun bind(message: ChatMessage) {
            messageText.text = message.text
            timestamp.text = timeFormat.format(Date(message.timestamp))
            senderName?.text = message.senderName
        }
    }

    class MessageDiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val VIEW_TYPE_INCOMING = 0
        private const val VIEW_TYPE_OUTGOING = 1
    }
}
