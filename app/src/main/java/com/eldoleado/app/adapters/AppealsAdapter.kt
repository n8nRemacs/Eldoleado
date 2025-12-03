package com.eldoleado.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eldoleado.app.R
import com.eldoleado.app.api.Appeal
import com.eldoleado.app.data.database.entities.AppealEntity

class AppealsAdapter(
    private var appeals: List<AppealItem> = emptyList(),
    private var entities: List<AppealEntity> = emptyList(),
    private val onAppealClick: (AppealEntity) -> Unit // full entity
) : RecyclerView.Adapter<AppealsAdapter.AppealViewHolder>() {

    // Для совместимости со старым кодом
    @Deprecated("Use updateAppealsFromEntities instead")
    fun updateAppeals(newAppeals: List<Appeal>) {
        appeals = newAppeals.map { it.toAppealItem() }
        notifyDataSetChanged()
    }

    fun updateAppealsFromEntities(newAppeals: List<AppealEntity>) {
        val oldList = appeals
        val newList = newAppeals.map { it.toAppealItem() }

        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newList.size
            override fun areItemsTheSame(oldPos: Int, newPos: Int) =
                oldList[oldPos].id == newList[newPos].id
            override fun areContentsTheSame(oldPos: Int, newPos: Int) =
                oldList[oldPos] == newList[newPos]
        })

        appeals = newList
        entities = newAppeals
        diffResult.dispatchUpdatesTo(this)
    }

    fun getEntityAt(position: Int): AppealEntity? = entities.getOrNull(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appeal, parent, false)
        return AppealViewHolder(view) { position ->
            getEntityAt(position)?.let { onAppealClick(it) }
        }
    }

    override fun onBindViewHolder(holder: AppealViewHolder, position: Int) {
        holder.bind(appeals[position], position)
    }

    override fun getItemCount() = appeals.size

    class AppealViewHolder(
        itemView: View,
        private val onPositionClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvClient: TextView = itemView.findViewById(R.id.tv_client_name)
        private val tvDevice: TextView = itemView.findViewById(R.id.tv_device_model)
        private val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
        private val tvAppealType: TextView = itemView.findViewById(R.id.tv_appeal_type)
        private val tvIssueType: TextView = itemView.findViewById(R.id.tv_issue_type)
        private val tvIssueDescription: TextView = itemView.findViewById(R.id.tv_issue_description)
        private val tvChannelBadge: TextView = itemView.findViewById(R.id.tv_channel_badge)

        fun bind(appeal: AppealItem, position: Int) {
            tvClient.text = appeal.clientName
                ?.takeIf { it.isNotBlank() }
                ?: appeal.clientPhone
                ?: "Без имени"

            tvStatus.text = formatStatus(appeal.stage, appeal.status)
            tvAppealType.setTextOrGone(formatAppealType(appeal))
            tvDevice.setTextOrGone(formatDevice(appeal))
            tvIssueType.setTextOrGone(formatIssueType(appeal))
            tvIssueDescription.setTextOrGone(formatIssueDescription(appeal))
            applyChannelBadge(appeal.channel ?: appeal.channelName)

            itemView.setOnClickListener {
                onPositionClick(position)
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

        private fun formatAppealType(appeal: AppealItem): String? {
            return appeal.appealType
                ?.takeIf { it.isNotBlank() }
                ?: appeal.repairTypeName
        }

        private fun formatDevice(appeal: AppealItem): String? {
            return listOfNotNull(appeal.deviceBrand, appeal.deviceModel)
                .joinToString(" ")
                .takeIf { it.isNotBlank() }
        }

        private fun formatIssueType(appeal: AppealItem): String? {
            return appeal.issueTypeName
                ?.takeIf { it.isNotBlank() }
                ?: appeal.repairTypeName
        }

        private fun formatIssueDescription(appeal: AppealItem): String? {
            return appeal.issueName
                ?.takeIf { it.isNotBlank() }
                ?: appeal.problemDescription
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

// Унифицированная модель для отображения
data class AppealItem(
    val id: String,
    val status: String?,
    val stage: String?,
    val channel: String?,
    val channelName: String?,
    val clientName: String?,
    val clientPhone: String?,
    val deviceBrand: String?,
    val deviceModel: String?,
    val appealType: String?,
    val repairTypeName: String?,
    val issueTypeName: String?,
    val issueName: String?,
    val problemDescription: String?
)

// Extension функции для конвертации
fun Appeal.toAppealItem() = AppealItem(
    id = this.id ?: "",
    status = this.status,
    stage = this.stage,
    channel = this.channel,
    channelName = this.channel_name,
    clientName = this.client?.name,
    clientPhone = this.client?.phone,
    deviceBrand = this.device?.brand,
    deviceModel = this.device?.model,
    appealType = this.appeal_type,
    repairTypeName = this.repair_type_name,
    issueTypeName = this.issue_type_name,
    issueName = this.issue_name,
    problemDescription = this.problem_description
)

fun AppealEntity.toAppealItem() = AppealItem(
    id = this.id,
    status = this.status,
    stage = this.stage,
    channel = this.channel,
    channelName = this.channelName,
    clientName = this.clientName,
    clientPhone = this.clientPhone,
    deviceBrand = this.deviceBrand,
    deviceModel = this.deviceModel,
    appealType = this.appealType,
    repairTypeName = this.repairTypeName,
    issueTypeName = this.issueTypeName,
    issueName = this.issueName,
    problemDescription = this.problemDescription
)
