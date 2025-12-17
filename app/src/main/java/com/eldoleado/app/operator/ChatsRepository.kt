package com.eldoleado.app.operator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eldoleado.app.operator.models.Chat
import com.eldoleado.app.operator.models.ChatMessage
import com.eldoleado.app.operator.models.DraftMessage
import org.json.JSONObject

/**
 * Repository for managing chats and messages.
 * Singleton pattern for shared state across activities/fragments.
 */
object ChatsRepository {

    // Chats list (chat_id -> Chat)
    private val _chats = mutableMapOf<String, Chat>()
    private val _chatsLiveData = MutableLiveData<List<Chat>>(emptyList())
    val chats: LiveData<List<Chat>> = _chatsLiveData

    // Messages per chat (chat_id -> list of messages)
    private val _messages = mutableMapOf<String, MutableList<ChatMessage>>()
    private val _messagesLiveData = mutableMapOf<String, MutableLiveData<List<ChatMessage>>>()

    // Current draft for approval
    private val _currentDraft = MutableLiveData<DraftMessage?>()
    val currentDraft: LiveData<DraftMessage?> = _currentDraft

    /**
     * Add incoming message from client.
     */
    fun addIncomingMessage(messageJson: String) {
        try {
            val json = JSONObject(messageJson)
            val message = ChatMessage.fromJson(json)

            // Add to messages
            addMessage(message)

            // Update or create chat
            updateChat(message)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Add outgoing message (sent by operator).
     */
    fun addOutgoingMessage(chatId: String, text: String, channel: String, serverId: String) {
        val message = ChatMessage(
            id = System.currentTimeMillis().toString(),
            chatId = chatId,
            channel = com.eldoleado.app.operator.models.Channel.fromId(channel),
            text = text,
            senderId = null,
            senderName = "Оператор",
            senderPhone = null,
            isIncoming = false,
            timestamp = System.currentTimeMillis(),
            serverId = serverId
        )
        addMessage(message)
    }

    /**
     * Set draft for approval.
     */
    fun setDraft(draftJson: String) {
        try {
            val json = JSONObject(draftJson)
            val draft = DraftMessage.fromJson(json)
            _currentDraft.postValue(draft)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Clear current draft.
     */
    fun clearDraft() {
        _currentDraft.postValue(null)
    }

    /**
     * Get messages LiveData for specific chat.
     */
    fun getMessagesLiveData(chatId: String): LiveData<List<ChatMessage>> {
        if (!_messagesLiveData.containsKey(chatId)) {
            _messagesLiveData[chatId] = MutableLiveData(emptyList())
        }
        return _messagesLiveData[chatId]!!
    }

    /**
     * Get messages for specific chat.
     */
    fun getMessages(chatId: String): List<ChatMessage> {
        return _messages[chatId]?.toList() ?: emptyList()
    }

    /**
     * Get chat by ID.
     */
    fun getChat(chatId: String): Chat? {
        return _chats[chatId]
    }

    private fun addMessage(message: ChatMessage) {
        val chatId = message.chatId

        if (!_messages.containsKey(chatId)) {
            _messages[chatId] = mutableListOf()
        }
        _messages[chatId]?.add(message)

        // Update LiveData
        if (!_messagesLiveData.containsKey(chatId)) {
            _messagesLiveData[chatId] = MutableLiveData()
        }
        _messagesLiveData[chatId]?.postValue(_messages[chatId]?.toList())
    }

    private fun updateChat(message: ChatMessage) {
        val chatId = message.chatId
        val existingChat = _chats[chatId]

        val chat = if (existingChat != null) {
            existingChat.copy(
                lastMessage = message.text,
                lastMessageTime = message.timestamp,
                unreadCount = if (message.isIncoming) existingChat.unreadCount + 1 else existingChat.unreadCount
            )
        } else {
            Chat(
                chatId = chatId,
                channel = message.channel,
                clientName = message.senderName ?: "Клиент",
                clientPhone = message.senderPhone,
                lastMessage = message.text,
                lastMessageTime = message.timestamp,
                unreadCount = if (message.isIncoming) 1 else 0,
                serverId = message.serverId
            )
        }

        _chats[chatId] = chat
        updateChatsLiveData()
    }

    /**
     * Mark chat as read.
     */
    fun markChatAsRead(chatId: String) {
        _chats[chatId]?.let { chat ->
            _chats[chatId] = chat.copy(unreadCount = 0)
            updateChatsLiveData()
        }
    }

    private fun updateChatsLiveData() {
        val sortedChats = _chats.values
            .sortedByDescending { it.lastMessageTime }
            .toList()
        _chatsLiveData.postValue(sortedChats)
    }

    /**
     * Clear all data.
     */
    fun clear() {
        _chats.clear()
        _messages.clear()
        _messagesLiveData.clear()
        _chatsLiveData.postValue(emptyList())
        _currentDraft.postValue(null)
    }
}
