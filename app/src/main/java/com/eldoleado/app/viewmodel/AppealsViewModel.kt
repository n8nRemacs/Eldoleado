package com.eldoleado.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eldoleado.app.data.database.entities.AppealEntity
import com.eldoleado.app.data.repository.AppealsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppealsViewModel(
    private val repository: AppealsRepository
) : ViewModel() {

    private val _appeals = MutableStateFlow<List<AppealEntity>>(emptyList())
    val appeals: StateFlow<List<AppealEntity>> = _appeals.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        observeAppeals()
    }

    private fun observeAppeals() {
        viewModelScope.launch {
            repository.getAppealsFlow().collect { appealsList ->
                _appeals.value = appealsList
            }
        }
    }

    fun refresh(operatorId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.refreshAppeals(operatorId)
            result.onFailure { e ->
                _error.value = e.message
            }

            _isLoading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }

    class Factory(private val repository: AppealsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AppealsViewModel::class.java)) {
                return AppealsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
