package com.eldoleado.app.adapters

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.eldoleado.app.R
import com.eldoleado.app.api.ChatMessageDto
import java.text.SimpleDateFormat
import java.util.*

class ChatMessagesAdapter : RecyclerView.Adapter<ChatMessagesAdapter.MessageViewHolder>() {

    private val messages: MutableList<ChatMessageDto> = mutableListOf()

    fun setMessages(newMessages: List<ChatMessageDto>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }

    fun addMessage(message: ChatMessageDto) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val container: LinearLayout = itemView.findViewById(R.id.messageContainer)
        private val bubble: CardView = itemView.findViewById(R.id.messageBubble)
        private val tvText: TextView = itemView.findViewById(R.id.tvMessageText)
        private val tvTime: TextView = itemView.findViewById(R.id.tvMessageTime)
        private val tvSender: TextView = itemView.findViewById(R.id.tvSenderName)
        private val spacerRight: View = itemView.findViewById(R.id.spacerRight)
        private val spacerLeft: View = itemView.findViewById(R.id.spacerLeft)

        companion object {
            // Client message colors (blue)
            private const val COLOR_CLIENT_BG = "#E3F2FD"
            private const val COLOR_CLIENT_SENDER = "#1565C0"

            // Operator message colors (orange)
            private const val COLOR_OPERATOR_BG = "#FFF3E0"
            private const val COLOR_OPERATOR_SENDER = "#E65100"
        }

        fun bind(message: ChatMessageDto) {
            val isOutgoing = message.direction == "out"

            // Text
            tvText.text = message.text ?: ""

            // Time
            tvTime.text = formatTime(message.timestamp ?: 0)

            // Sender name
            val senderName = when {
                isOutgoing -> message.sender_name ?: "Оператор"
                else -> message.sender_name ?: "Клиент"
            }
            tvSender.text = senderName
            tvSender.visibility = View.VISIBLE

            // Layout params for spacers
            val spacerRightParams = spacerRight.layoutParams as LinearLayout.LayoutParams
            val spacerLeftParams = spacerLeft.layoutParams as LinearLayout.LayoutParams

            if (isOutgoing) {
                // Operator message: right aligned, orange background
                container.gravity = Gravity.END
                bubble.setCardBackgroundColor(Color.parseColor(COLOR_OPERATOR_BG))
                tvSender.setTextColor(Color.parseColor(COLOR_OPERATOR_SENDER))
                tvSender.gravity = Gravity.END
                tvTime.gravity = Gravity.END

                // Show left spacer, hide right
                spacerLeftParams.weight = 0.2f
                spacerLeft.visibility = View.VISIBLE
                spacerRightParams.weight = 0f
                spacerRight.visibility = View.GONE

                // Reorder views: spacerLeft, bubble, (no spacerRight)
                container.removeAllViews()
                container.addView(spacerLeft)
                container.addView(bubble)
            } else {
                // Client message: left aligned, blue background
                container.gravity = Gravity.START
                bubble.setCardBackgroundColor(Color.parseColor(COLOR_CLIENT_BG))
                tvSender.setTextColor(Color.parseColor(COLOR_CLIENT_SENDER))
                tvSender.gravity = Gravity.START
                tvTime.gravity = Gravity.START

                // Show right spacer, hide left
                spacerRightParams.weight = 0.2f
                spacerRight.visibility = View.VISIBLE
                spacerLeftParams.weight = 0f
                spacerLeft.visibility = View.GONE

                // Reorder views: bubble, spacerRight
                container.removeAllViews()
                container.addView(bubble)
                container.addView(spacerRight)
            }

            spacerRight.layoutParams = spacerRightParams
            spacerLeft.layoutParams = spacerLeftParams
        }

        private fun formatTime(timestamp: Long): String {
            if (timestamp == 0L) return ""
            return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))
        }
    }
}
