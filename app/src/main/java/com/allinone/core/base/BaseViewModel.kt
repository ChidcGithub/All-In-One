package com.allinone.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    protected fun updateState(state: UiState) {
        viewModelScope.launch {
            _uiState.value = state
        }
    }

    protected fun setLoading(loading: Boolean) {
        viewModelScope.launch {
            _uiState.value = if (loading) UiState.Loading else UiState.Idle
        }
    }

    protected fun setError(message: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Error(message)
        }
    }

    protected fun clearState() {
        viewModelScope.launch {
            _uiState.value = UiState.Idle
        }
    }
}

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Error(val message: String) : UiState()
    data class Success<T>(val data: T) : UiState()
}
