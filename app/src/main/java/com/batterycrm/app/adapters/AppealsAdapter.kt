package com.batterycrm.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.batterycrm.app.R
import com.batterycrm.app.api.Appeal

class AppealsAdapter(
    private var appeals: List<Appeal>,
    private val onAppealClick: (Appeal) -> Unit
) : RecyclerView.Adapter<AppealsAdapter.AppealViewHolder>() {

    fun updateAppeals(newAppeals: List<Appeal>) {
        appeals = newAppeals
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appeal, parent, false)
        return AppealViewHolder(view, onAppealClick)
    }

    override fun onBindViewHolder(holder: AppealViewHolder, position: Int) {
        holder.bind(appeals[position])
    }

    override fun getItemCount() = appeals.size

    class AppealViewHolder(
        itemView: View,
        private val onAppealClick: (Appeal) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvClient: TextView = itemView.findViewById(R.id.tv_client_name)
        private val tvDevice: TextView = itemView.findViewById(R.id.tv_device_model)
        private val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
        private val tvAppealType: TextView = itemView.findViewById(R.id.tv_appeal_type)
        private val tvIssueType: TextView = itemView.findViewById(R.id.tv_issue_type)
        private val tvIssueDescription: TextView = itemView.findViewById(R.id.tv_issue_description)
        private val tvChannelBadge: TextView = itemView.findViewById(R.id.tv_channel_badge)

        fun bind(appeal: Appeal) {
            tvClient.text = appeal.client?.name
                ?.takeIf { it.isNotBlank() }
                ?: appeal.client?.phone
                ?: "Без имени"

            tvStatus.text = formatStatus(appeal.stage, appeal.status)
            tvAppealType.setTextOrGone(formatAppealType(appeal))
            tvDevice.setTextOrGone(formatDevice(appeal))
            tvIssueType.setTextOrGone(formatIssueType(appeal))
            tvIssueDescription.setTextOrGone(formatIssueDescription(appeal))
            applyChannelBadge(appeal.channel ?: appeal.channel_name)

            itemView.setOnClickListener {
                onAppealClick(appeal)
            }
        }

        private fun formatStatus(stage: String?, status: String?): String {
            val normalized = (stage ?: status)?.lowercase()?.trim()
            return when (normalized) {
                null, "", "null" -> "Без статуса"
                "new", "первичный контакт" -> "Первичный контакт"
                "in_progress", "в работе" -> "В работе"
                "completed", "закрыто" -> "Завершено"
                else -> stage ?: status ?: "Без статуса"
            }
        }

        private fun formatAppealType(appeal: Appeal): String? {
            return appeal.appeal_type
                ?.takeIf { it.isNotBlank() }
                ?: appeal.repair_type_name
        }

        private fun formatDevice(appeal: Appeal): String? {
            return listOfNotNull(appeal.device?.brand, appeal.device?.model)
                .joinToString(" ")
                .takeIf { it.isNotBlank() }
        }

        private fun formatIssueType(appeal: Appeal): String? {
            return appeal.issue_type_name
                ?.takeIf { it.isNotBlank() }
                ?: appeal.repair_type_name
        }

        private fun formatIssueDescription(appeal: Appeal): String? {
            return appeal.issue_name
                ?.takeIf { it.isNotBlank() }
                ?: appeal.problem_description
        }

        private fun applyChannelBadge(channelRaw: String?) {
            val (text, colorRes) = resolveChannelBadge(channelRaw)
            tvChannelBadge.text = text

            val background = AppCompatResources.getDrawable(itemView.context, R.drawable.bg_channel_badge)
                ?.mutate()
            val tintColor = ContextCompat.getColor(itemView.context, colorRes)

            if (background != null) {
                DrawableCompat.setTint(background, tintColor)
                ViewCompat.setBackground(tvChannelBadge, background)
            }
        }

        private fun resolveChannelBadge(channelRaw: String?): Pair<String, Int> {
            val normalized = channelRaw?.lowercase()?.trim()
            return when {
                normalized.isNullOrBlank() -> "" to R.color.channel_generic
                normalized.contains("whatsapp") || normalized == "wa" ->
                    "WA" to R.color.channel_whatsapp
                normalized.contains("telegram") || normalized == "tg" ->
                    "TG" to R.color.channel_telegram
                normalized.contains("avito") ->
                    "AV" to R.color.channel_avito
                else -> normalized.take(2)
                    .uppercase()
                    .takeIf { it.isNotBlank() }
                    ?.let { it to R.color.channel_generic }
                    ?: ("" to R.color.channel_generic)
            }
        }

        private fun TextView.setTextOrGone(value: String?) {
            if (value.isNullOrBlank()) {
                visibility = View.GONE
            } else {
                text = value
                visibility = View.VISIBLE
            }
        }
    }
}
