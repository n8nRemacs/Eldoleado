package com.eldoleado.app.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eldoleado.app.R
import com.eldoleado.app.data.database.entities.DialogEntity
import java.text.SimpleDateFormat
import java.util.*

class DialogsAdapter(
    private val onDialogClick: (DialogEntity) -> Unit
) : RecyclerView.Adapter<DialogsAdapter.DialogViewHolder>() {

    private var dialogs: List<DialogEntity> = emptyList()

    fun updateDialogs(newDialogs: List<DialogEntity>) {
        val oldList = dialogs
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newDialogs.size
            override fun areItemsTheSame(oldPos: Int, newPos: Int) =
                oldList[oldPos].id == newDialogs[newPos].id
            override fun areContentsTheSame(oldPos: Int, newPos: Int) =
                oldList[oldPos] == newDialogs[newPos]
        })
        dialogs = newDialogs
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dialog, parent, false)
        return DialogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        holder.bind(dialogs[position], onDialogClick)
    }

    override fun getItemCount() = dialogs.size

    class DialogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvClientName)
        private val tvLastMessage: TextView = itemView.findViewById(R.id.tvLastMessage)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        private val tvUnread: TextView = itemView.findViewById(R.id.tvUnreadCount)
        private val tvChannelBadge: TextView = itemView.findViewById(R.id.tvChannelBadge)
        private val ivVoice: ImageView = itemView.findViewById(R.id.ivVoiceIndicator)

        fun bind(dialog: DialogEntity, onClick: (DialogEntity) -> Unit) {
            val hasUnread = dialog.unreadCount > 0

            // Client name or phone - bold if unread
            tvName.text = dialog.clientName
                ?.takeIf { it.isNotBlank() }
                ?: dialog.clientPhone
                ?: "Клиент"
            tvName.setTypeface(null, if (hasUnread) Typeface.BOLD else Typeface.NORMAL)

            // Last message preview - bold if unread
            if (dialog.lastMessageIsVoice) {
                tvLastMessage.text = "Голосовое сообщение"
                ivVoice.visibility = View.VISIBLE
            } else {
                tvLastMessage.text = dialog.lastMessageText ?: ""
                ivVoice.visibility = View.GONE
            }
            tvLastMessage.setTypeface(null, if (hasUnread) Typeface.BOLD else Typeface.NORMAL)
            tvLastMessage.setTextColor(ContextCompat.getColor(
                itemView.context,
                if (hasUnread) android.R.color.black else R.color.text_secondary
            ))

            // Time
            tvTime.text = formatTime(dialog.lastMessageTime)

            // Unread count badge
            if (hasUnread) {
                tvUnread.visibility = View.VISIBLE
                tvUnread.text = if (dialog.unreadCount > 99) "99+" else dialog.unreadCount.toString()
            } else {
                tvUnread.visibility = View.GONE
            }

            // Channel badge
            applyChannelBadge(dialog.channel)

            itemView.setOnClickListener { onClick(dialog) }
        }

        private fun formatTime(timestamp: Long): String {
            if (timestamp == 0L) return ""

            val now = System.currentTimeMillis()
            val diff = now - timestamp

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp

            val today = Calendar.getInstance()
            val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }

            return when {
                // Today - show time
                calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) -> {
                    SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))
                }
                // Yesterday
                calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR) -> {
                    "Вчера"
                }
                // This year - show date without year
                calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) -> {
                    SimpleDateFormat("d MMM", Locale.getDefault()).format(Date(timestamp))
                }
                // Other - full date
                else -> {
                    SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(Date(timestamp))
                }
            }
        }

        private fun applyChannelBadge(channel: String?) {
            val (text, colorRes) = when (channel?.lowercase()) {
                "whatsapp", "wa" -> "WA" to R.color.channel_whatsapp
                "telegram", "tg" -> "TG" to R.color.channel_telegram
                "avito" -> "AV" to R.color.channel_avito
                "vk" -> "VK" to R.color.channel_vk
                "max" -> "MX" to R.color.channel_max
                else -> (channel?.take(2)?.uppercase() ?: "") to R.color.channel_generic
            }

            tvChannelBadge.text = text
            if (text.isNotEmpty()) {
                tvChannelBadge.visibility = View.VISIBLE
                tvChannelBadge.setBackgroundColor(ContextCompat.getColor(itemView.context, colorRes))
            } else {
                tvChannelBadge.visibility = View.GONE
            }
        }
    }
}
