package com.eldoleado.app.data

data class Message(
    val id: String,
    val text: String,
    val isFromOperator: Boolean,
    val timestamp: String,
    val mediaType: String? = null,
    val mediaUrl: String? = null
)
