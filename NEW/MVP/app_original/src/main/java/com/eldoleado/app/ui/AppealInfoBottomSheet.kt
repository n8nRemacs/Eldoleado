package com.eldoleado.app.ui

import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.eldoleado.app.R
import com.eldoleado.app.api.AppealDeviceDto
import com.eldoleado.app.data.database.entities.AppealEntity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AppealInfoBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheet = (dialogInterface as BottomSheetDialog)
                .findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                // Start collapsed, swipe up to expand
                val displayMetrics = resources.displayMetrics
                val peekHeightPx = (displayMetrics.heightPixels * 0.35).toInt() // 35% of screen
                behavior.peekHeight = peekHeightPx
                behavior.isFitToContents = true
                behavior.isDraggable = true
                behavior.skipCollapsed = false
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        return dialog
    }

    companion object {
        private const val ARG_CLIENT_NAME = "client_name"
        private const val ARG_CLIENT_PHONE = "client_phone"
        private const val ARG_STATUS = "status"
        private const val ARG_STAGE = "stage"
        private const val ARG_DEVICE_BRAND = "device_brand"
        private const val ARG_DEVICE_MODEL = "device_model"
        private const val ARG_APPEAL_TYPE = "appeal_type"
        private const val ARG_REPAIR_TYPE = "repair_type"
        private const val ARG_ISSUE_TYPE = "issue_type"
        private const val ARG_PROBLEM_DESC = "problem_desc"
        private const val ARG_PARTS_OWNER = "parts_owner"
        private const val ARG_CHANNEL = "channel"
        private const val ARG_DEVICES_JSON = "devices_json"

        fun newInstance(appeal: AppealEntity): AppealInfoBottomSheet {
            return AppealInfoBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(ARG_CLIENT_NAME, appeal.clientName)
                    putString(ARG_CLIENT_PHONE, appeal.clientPhone)
                    putString(ARG_STATUS, appeal.status)
                    putString(ARG_STAGE, appeal.stage)
                    putString(ARG_DEVICE_BRAND, appeal.deviceBrand)
                    putString(ARG_DEVICE_MODEL, appeal.deviceModel)
                    putString(ARG_APPEAL_TYPE, appeal.appealType)
                    putString(ARG_REPAIR_TYPE, appeal.repairTypeName)
                    putString(ARG_ISSUE_TYPE, appeal.issueTypeName)
                    putString(ARG_PROBLEM_DESC, appeal.problemDescription)
                    putString(ARG_PARTS_OWNER, appeal.partsOwner)
                    putString(ARG_CHANNEL, appeal.channel ?: appeal.channelName)
                    putString(ARG_DEVICES_JSON, appeal.devicesJson)
                }
            }
        }
    }

    private var devices: List<AppealDeviceDto> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_appeal_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments ?: return

        view.findViewById<TextView>(R.id.tvClientNameValue).text =
            args.getString(ARG_CLIENT_NAME)?.takeIf { it.isNotBlank() }
        view.findViewById<TextView>(R.id.tvClientPhone).text =
            args.getString(ARG_CLIENT_PHONE)?.takeIf { it.isNotBlank() }
        view.findViewById<TextView>(R.id.tvChannel).text =
            translateChannel(args.getString(ARG_CHANNEL))
        view.findViewById<TextView>(R.id.tvStatus).text =
            formatStatus(args.getString(ARG_STAGE), args.getString(ARG_STATUS))
        view.findViewById<TextView>(R.id.tvProblemDescription).text =
            args.getString(ARG_PROBLEM_DESC)?.takeIf { it.isNotBlank() }

        // Parse devices from JSON
        val devicesJson = args.getString(ARG_DEVICES_JSON)
        devices = parseDevicesJson(devicesJson)

        val devicesContainer = view.findViewById<LinearLayout>(R.id.devicesContainer)
        val devicesListContainer = view.findViewById<LinearLayout>(R.id.devicesListContainer)
        val devicesHeader = view.findViewById<TextView>(R.id.tvDevicesHeader)
        val legacyContainer = view.findViewById<LinearLayout>(R.id.legacyDeviceContainer)

        if (devices.isNotEmpty()) {
            devicesContainer.visibility = View.VISIBLE
            devicesHeader.visibility = View.VISIBLE
            legacyContainer.visibility = View.GONE
            populateDevices(devicesListContainer)
        } else {
            devicesContainer.visibility = View.GONE
            legacyContainer.visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.tvDeviceBrand).text =
                args.getString(ARG_DEVICE_BRAND)?.takeIf { it.isNotBlank() }
            view.findViewById<TextView>(R.id.tvDeviceModel).text =
                args.getString(ARG_DEVICE_MODEL)?.takeIf { it.isNotBlank() }
            view.findViewById<TextView>(R.id.tvAppealType).text =
                translateAppealType(args.getString(ARG_APPEAL_TYPE))
            view.findViewById<TextView>(R.id.tvRepairType).text =
                args.getString(ARG_REPAIR_TYPE)?.takeIf { it.isNotBlank() }
            view.findViewById<TextView>(R.id.tvIssueType).text =
                args.getString(ARG_ISSUE_TYPE)?.takeIf { it.isNotBlank() }
            view.findViewById<TextView>(R.id.tvPartsOwner).text =
                translatePartsOwner(args.getString(ARG_PARTS_OWNER))
        }
    }

    private fun parseDevicesJson(json: String?): List<AppealDeviceDto> {
        if (json.isNullOrBlank()) return emptyList()
        return try {
            com.google.gson.Gson().fromJson(json, Array<AppealDeviceDto>::class.java).toList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun populateDevices(container: LinearLayout) {
        container.removeAllViews()
        val context = container.context

        devices.forEachIndexed { index, device ->
            val deviceName = buildString {
                if (!device.brand_name.isNullOrBlank()) append(device.brand_name)
                if (!device.model_name.isNullOrBlank()) {
                    if (isNotEmpty()) append(" ")
                    append(device.model_name)
                }
                if (!device.phone_model.isNullOrBlank() && isEmpty()) append(device.phone_model)
                if (isEmpty()) append("Устройство ${index + 1}")
            }

            val deviceHeader = TextView(context).apply {
                text = deviceName
                textSize = 12f
                setTextColor(0xFF333333.toInt())
                setTypeface(null, Typeface.BOLD)
                setPadding(0, if (index > 0) 16 else 0, 0, 4)
            }
            container.addView(deviceHeader)

            device.repairs?.forEach { repair ->
                val repairText = buildString {
                    repair.repair_category_name?.let { append(it) }
                    repair.issue_type_name?.let {
                        if (isNotEmpty()) append(" - ")
                        append(it)
                    }
                    repair.parts_owner?.let {
                        val translated = translatePartsOwner(it)
                        if (translated != null) {
                            if (isNotEmpty()) append(" (")
                            append(translated)
                            append(")")
                        }
                    }
                }
                if (repairText.isNotBlank()) {
                    val repairView = TextView(context).apply {
                        text = "  \u2022 $repairText"
                        textSize = 11f
                        setTextColor(0xFF666666.toInt())
                        setPadding(8, 2, 0, 2)
                    }
                    container.addView(repairView)
                }
            }

            if (device.repairs.isNullOrEmpty()) {
                val noRepairs = TextView(context).apply {
                    text = "  \u2022 Ремонты не указаны"
                    textSize = 11f
                    setTextColor(0xFF999999.toInt())
                    setPadding(8, 2, 0, 2)
                }
                container.addView(noRepairs)
            }
        }
    }

    private fun formatStatus(stage: String?, status: String?): String? {
        val normalized = (stage ?: status)?.lowercase()?.trim()
        return when {
            normalized.isNullOrBlank() || normalized == "null" -> null
            normalized == "new" -> "Первичный контакт"
            normalized == "in_progress" -> "В работе"
            normalized == "completed" -> "Завершено"
            normalized.contains("информация") || normalized.contains("собран") -> "Информация собрана"
            else -> stage ?: status
        }
    }

    private fun translateAppealType(type: String?): String? {
        val normalized = type?.lowercase()?.trim()
        return when (normalized) {
            null, "", "null" -> null
            "repair" -> "Ремонт"
            "consultation" -> "Консультация"
            "greeting" -> "Приветствие"
            "spam" -> "Спам"
            "other" -> "Другое"
            else -> type?.replaceFirstChar { it.uppercase() }
        }
    }

    private fun translatePartsOwner(value: String?): String? {
        val normalized = value?.lowercase()?.trim()
        return when (normalized) {
            null, "", "null" -> null
            "workshop" -> "Мастерской"
            "client" -> "Клиента"
            else -> value
        }
    }

    private fun translateChannel(channel: String?): String? {
        val normalized = channel?.lowercase()?.trim()
        return when {
            normalized.isNullOrBlank() -> null
            normalized.contains("whatsapp") || normalized == "wa" -> "WhatsApp"
            normalized.contains("telegram") || normalized == "tg" -> "Telegram"
            normalized.contains("avito") -> "Avito"
            normalized.contains("vk") -> "ВКонтакте"
            else -> channel
        }
    }
}
