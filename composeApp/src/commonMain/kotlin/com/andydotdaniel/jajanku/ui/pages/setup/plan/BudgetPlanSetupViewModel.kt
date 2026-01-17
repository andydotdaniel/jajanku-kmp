package com.andydotdaniel.jajanku.ui.pages.setup.plan

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

sealed class BudgetPlanSetupEvent {
    class NavigateToReviewBudget() : BudgetPlanSetupEvent()
}

class BudgetPlanSetupViewModel: ViewModel() {

    private val _uiEvents = Channel<BudgetPlanSetupEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun submit() {
        _uiEvents.trySend(BudgetPlanSetupEvent.NavigateToReviewBudget())
    }

}