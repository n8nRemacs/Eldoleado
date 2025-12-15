package com.eldoleado.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eldoleado.app.data.database.entities.AppealEntity
import com.eldoleado.app.data.database.entities.MessageEntity
import com.eldoleado.app.data.repository.AppealDetailState
import com.eldoleado.app.data.repository.AppealsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppealDetailViewModel(
    private val repository: AppealsRepository,
    private val appealId: String
) : ViewModel() {

    private val _state = MutableStateFlow<AppealDetailUiState>(AppealDetailUiState.Loading)
    val state: StateFlow<AppealDetailUiState> = _state.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadDetail()
    }

    fun loadDetail() {
        viewModelScope.launch {
            _state.value = AppealDetailUiState.Loading

            when (val result = repository.getAppealDetail(appealId)) {
                is AppealDetailState.Success -> {
                    _state.value = AppealDetailUiState.Success(
                        appeal = result.appeal,
                        messages = result.messages
                    )
                }
                is AppealDetailState.Error -> {
                    _state.value = AppealDetailUiState.Error(result.message)
                }
                AppealDetailState.Loading -> {
                    _state.value = AppealDetailUiState.Loading
                }
            }
        }
    }

    fun forceRefresh() {
        viewModelScope.launch {
            _isRefreshing.value = true

            when (val result = repository.forceRefreshAppealDetail(appealId)) {
                is AppealDetailState.Success -> {
                    _state.value = AppealDetailUiState.Success(
                        appeal = result.appeal,
                        messages = result.messages
                    )
                }
                is AppealDetailState.Error -> {
                    // При ошибке обновления не меняем состояние, просто показываем ошибку
                    val currentState = _state.value
                    if (currentState is AppealDetailUiState.Success) {
                        _state.value = currentState.copy(refreshError = result.message)
                    } else {
                        _state.value = AppealDetailUiState.Error(result.message)
                    }
                }
                AppealDetailState.Loading -> {}
            }

            _isRefreshing.value = false
        }
    }

    fun invalidateAndReload() {
        viewModelScope.launch {
            repository.invalidateAppealCache(appealId)
            loadDetail()
        }
    }

    fun clearRefreshError() {
        val currentState = _state.value
        if (currentState is AppealDetailUiState.Success) {
            _state.value = currentState.copy(refreshError = null)
        }
    }

    class Factory(
        private val repository: AppealsRepository,
        private val appealId: String
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AppealDetailViewModel::class.java)) {
                return AppealDetailViewModel(repository, appealId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

sealed class AppealDetailUiState {
    object Loading : AppealDetailUiState()
    data class Success(
        val appeal: AppealEntity,
        val messages: List<MessageEntity>,
        val refreshError: String? = null
    ) : AppealDetailUiState()
    data class Error(val message: String) : AppealDetailUiState()
}
