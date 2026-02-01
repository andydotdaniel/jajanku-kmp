package com.andydotdaniel.jajanku.ui.pages.home

import androidx.lifecycle.ViewModel
import com.andydotdaniel.jajanku.utils.NumberFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel: ViewModel() {

    data class UIState(
        val selectedBudgetView: Int = 0,
        val remainingBudget: String
    )
    private val numberFormatter = NumberFormatter()
    private val _uiState = MutableStateFlow(UIState(
        remainingBudget = numberFormatter.format(2300000.toDouble()),
    ))
    val uiState: StateFlow<HomeViewModel.UIState> = _uiState.asStateFlow()

    fun selectBudgetView(index: Int) {

    }


}