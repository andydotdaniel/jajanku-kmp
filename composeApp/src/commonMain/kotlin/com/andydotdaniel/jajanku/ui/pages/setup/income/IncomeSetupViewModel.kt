package com.andydotdaniel.jajanku.ui.pages.setup.income

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

sealed class IncomeSetupEvent {
    class NavigateToBudgetPlanSetup() : IncomeSetupEvent()
}

class IncomeSetupViewModel: ViewModel() {

    private val _uiEvents = Channel<IncomeSetupEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun submit() {
        _uiEvents.trySend(IncomeSetupEvent.NavigateToBudgetPlanSetup())
    }

}