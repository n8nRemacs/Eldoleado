package com.batterycrm.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        
        private val tvNumber: TextView = itemView.findViewById(R.id.tv_appeal_number)
        private val tvClient: TextView = itemView.findViewById(R.id.tv_client_name)
        private val tvDevice: TextView = itemView.findViewById(R.id.tv_device_model)
        private val tvLastMessage: TextView = itemView.findViewById(R.id.tv_last_message)
        private val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
        
        fun bind(appeal: Appeal) {
            tvNumber.text = "#${appeal.number ?: "--"}"
            tvClient.text = appeal.client?.name ?: appeal.client?.phone ?: "Unknown"
            tvDevice.text = listOfNotNull(appeal.device?.brand, appeal.device?.model)
                .joinToString(" ")
                .ifBlank { "—" }
            tvLastMessage.text = appeal.last_message ?: "Нет сообщений"
            tvStatus.text = when (appeal.status) {
                "new" -> "Новое"
                "in_progress" -> "В работе"
                "completed" -> "Завершено"
                else -> appeal.status ?: "—"
            }
            
            itemView.setOnClickListener {
                onAppealClick(appeal)
            }
        }
    }
}
