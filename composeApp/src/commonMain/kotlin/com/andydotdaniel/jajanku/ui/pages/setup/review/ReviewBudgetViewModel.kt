package com.andydotdaniel.jajanku.ui.pages.setup.review

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReviewBudgetViewModel: ViewModel() {

    data class UIState(
        val selectedBudgetView: Int = 0
    )

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun selectBudgetView(index: Int) {
        _uiState.value = _uiState.value.copy(selectedBudgetView = index)
    }

}