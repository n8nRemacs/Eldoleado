package com.eldoleado.app.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eldoleado.app.R
import com.eldoleado.app.api.AppealDeviceDto
import com.google.android.material.card.MaterialCardView

class DevicesAdapter(
    private val onDeviceClick: (AppealDeviceDto) -> Unit
) : ListAdapter<DeviceItem, DevicesAdapter.DeviceViewHolder>(DeviceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_device_card, parent, false)
        return DeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(getItem(position), onDeviceClick)
    }

    class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val card: MaterialCardView = itemView.findViewById(R.id.deviceCard)
        private val deviceName: TextView = itemView.findViewById(R.id.deviceName)
        private val focusIndicator: TextView = itemView.findViewById(R.id.focusIndicator)
        private val repairsContainer: LinearLayout = itemView.findViewById(R.id.repairsContainer)
        private val noRepairsText: TextView = itemView.findViewById(R.id.noRepairsText)

        fun bind(item: DeviceItem, onDeviceClick: (AppealDeviceDto) -> Unit) {
            val device = item.device
            val context = itemView.context

            // Device name
            val name = buildString {
                if (!device.brand_name.isNullOrBlank()) append(device.brand_name)
                if (!device.model_name.isNullOrBlank()) {
                    if (isNotEmpty()) append(" ")
                    append(device.model_name)
                }
                if (!device.phone_model.isNullOrBlank() && isEmpty()) append(device.phone_model)
                if (isEmpty()) append("Устройство ${item.index + 1}")
            }
            deviceName.text = name

            // Focus indicator
            if (item.isFocused) {
                focusIndicator.visibility = View.VISIBLE
                card.strokeColor = ContextCompat.getColor(context, R.color.focus_green)
                card.strokeWidth = 4
                card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.focus_green_light))
            } else {
                focusIndicator.visibility = View.GONE
                card.strokeColor = ContextCompat.getColor(context, R.color.card_border)
                card.strokeWidth = 2
                card.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
            }

            // Repairs
            repairsContainer.removeAllViews()
            val repairs = device.repairs ?: emptyList()

            if (repairs.isEmpty()) {
                noRepairsText.visibility = View.VISIBLE
            } else {
                noRepairsText.visibility = View.GONE
                repairs.take(3).forEach { repair ->
                    val repairText = repair.repair_category_name
                        ?: repair.issue_type_name
                        ?: "Ремонт"

                    val repairView = TextView(context).apply {
                        text = "\u2022 $repairText"
                        textSize = 11f
                        setTextColor(ContextCompat.getColor(context, R.color.text_secondary))
                        maxLines = 1
                    }
                    repairsContainer.addView(repairView)
                }

                if (repairs.size > 3) {
                    val moreView = TextView(context).apply {
                        text = "+${repairs.size - 3} ещё"
                        textSize = 10f
                        setTextColor(ContextCompat.getColor(context, R.color.primary_blue))
                    }
                    repairsContainer.addView(moreView)
                }
            }

            // Click listener
            card.setOnClickListener {
                onDeviceClick(device)
            }
        }
    }

    class DeviceDiffCallback : DiffUtil.ItemCallback<DeviceItem>() {
        override fun areItemsTheSame(oldItem: DeviceItem, newItem: DeviceItem): Boolean {
            return oldItem.device.id == newItem.device.id
        }

        override fun areContentsTheSame(oldItem: DeviceItem, newItem: DeviceItem): Boolean {
            return oldItem == newItem
        }
    }
}

data class DeviceItem(
    val device: AppealDeviceDto,
    val index: Int,
    val isFocused: Boolean
)
