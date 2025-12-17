package com.eldoleado.app.operator

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.eldoleado.app.R

/**
 * Dialog for approving/editing normalized message drafts.
 * Shows original text and normalized text side by side.
 * Operator can approve, edit, or reject the draft.
 */
class DraftApprovalDialog : DialogFragment() {

    private lateinit var originalText: TextView
    private lateinit var normalizedText: EditText
    private lateinit var btnApprove: Button
    private lateinit var btnReject: Button

    private var draftId: String = ""
    private var chatId: String = ""
    private var channel: String = ""
    private var serverId: String = ""

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Theme_Eldoleado_Dialog)

        arguments?.let {
            draftId = it.getString(ARG_DRAFT_ID, "")
            chatId = it.getString(ARG_CHAT_ID, "")
            channel = it.getString(ARG_CHANNEL, "")
            serverId = it.getString(ARG_SERVER_ID, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_draft_approval, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        originalText = view.findViewById(R.id.text_original)
        normalizedText = view.findViewById(R.id.edit_normalized)
        btnApprove = view.findViewById(R.id.btn_approve)
        btnReject = view.findViewById(R.id.btn_reject)

        // Set texts from arguments
        arguments?.let {
            originalText.text = it.getString(ARG_ORIGINAL_TEXT, "")
            normalizedText.setText(it.getString(ARG_NORMALIZED_TEXT, ""))
        }

        btnApprove.setOnClickListener {
            approveMessage()
        }

        btnReject.setOnClickListener {
            rejectMessage()
        }
    }

    override fun onStart() {
        super.onStart()
        // Make dialog wider
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

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

    private fun approveMessage() {
        val finalText = normalizedText.text.toString().trim()
        if (finalText.isEmpty()) return

        wsService?.approveMessage(
            messageId = draftId,
            chatId = chatId,
            channel = channel,
            finalText = finalText,
            serverId = serverId
        )

        // Add to local messages as sent
        ChatsRepository.addOutgoingMessage(
            chatId = chatId,
            text = finalText,
            channel = channel,
            serverId = serverId
        )

        ChatsRepository.clearDraft()
        dismiss()
    }

    private fun rejectMessage() {
        ChatsRepository.clearDraft()
        dismiss()
    }

    companion object {
        private const val ARG_DRAFT_ID = "draft_id"
        private const val ARG_CHAT_ID = "chat_id"
        private const val ARG_CHANNEL = "channel"
        private const val ARG_ORIGINAL_TEXT = "original_text"
        private const val ARG_NORMALIZED_TEXT = "normalized_text"
        private const val ARG_SERVER_ID = "server_id"

        fun newInstance(
            draftId: String,
            chatId: String,
            channel: String,
            originalText: String,
            normalizedText: String,
            serverId: String
        ): DraftApprovalDialog {
            return DraftApprovalDialog().apply {
                arguments = Bundle().apply {
                    putString(ARG_DRAFT_ID, draftId)
                    putString(ARG_CHAT_ID, chatId)
                    putString(ARG_CHANNEL, channel)
                    putString(ARG_ORIGINAL_TEXT, originalText)
                    putString(ARG_NORMALIZED_TEXT, normalizedText)
                    putString(ARG_SERVER_ID, serverId)
                }
            }
        }
    }
}
