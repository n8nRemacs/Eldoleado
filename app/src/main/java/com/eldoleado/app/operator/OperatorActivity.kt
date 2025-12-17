package com.eldoleado.app.operator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.eldoleado.app.R

/**
 * Main activity for operator interface.
 * Hosts ChatsListFragment and ChatFragment.
 * Starts OperatorWebSocketService on creation.
 */
class OperatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operator)

        // Start WebSocket service
        OperatorWebSocketService.start(this)

        // Show chats list
        if (savedInstanceState == null) {
            showChatsList()
        }
    }

    private fun showChatsList() {
        val fragment = ChatsListFragment.newInstance()
        fragment.setOnChatSelectedListener { chat ->
            openChat(chat.chatId)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun openChat(chatId: String) {
        val fragment = ChatFragment.newInstance(chatId)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("chat")
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            OperatorWebSocketService.stop(this)
        }
    }
}
