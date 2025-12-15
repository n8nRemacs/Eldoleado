package com.eldoleado.app

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * События обновления заявок для realtime синхронизации между FCM и UI
 */
sealed class AppealUpdateEvent {
    data class NewMessage(val appealId: String) : AppealUpdateEvent()
    data class DraftReady(val appealId: String, val draftText: String?) : AppealUpdateEvent()
    data class AppealUpdated(val appealId: String) : AppealUpdateEvent()
    object AllAppealsUpdated : AppealUpdateEvent()
}

/**
 * Синглтон для публикации и подписки на события обновления заявок
 */
object AppealEventBus {
    private val _events = MutableSharedFlow<AppealUpdateEvent>(extraBufferCapacity = 10)
    val events: SharedFlow<AppealUpdateEvent> = _events.asSharedFlow()

    fun post(event: AppealUpdateEvent) {
        _events.tryEmit(event)
    }
}
