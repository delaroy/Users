package com.delaroystudios.vpd.util

import com.delaroystudios.vpd.common.Event


sealed interface UiState {
    object Loading : UiState
    object Success : UiState
    object Nothing : UiState
    data class Error(
        val errorMessage: Event<String>
    ) : UiState
}
