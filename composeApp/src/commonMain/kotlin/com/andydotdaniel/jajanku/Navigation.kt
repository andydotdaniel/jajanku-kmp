package com.andydotdaniel.jajanku

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.andydotdaniel.jajanku.data.repository.BudgetPlanRepository
import com.andydotdaniel.jajanku.ui.pages.home.Home
import com.andydotdaniel.jajanku.ui.pages.setup.income.IncomeSetup
import com.andydotdaniel.jajanku.utils.AppColor
import kotlinx.coroutines.flow.take

sealed interface NavigationState {
    object Loading : NavigationState
    object Ready: NavigationState

}

class Launcher(private val budgetRepository: BudgetPlanRepository) {

    @Composable
    fun Navigation() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColor.Black
        ) {
            val navigationState by budgetRepository.getSavedBudgetPlan().take(1).collectAsState(initial = NavigationState.Loading)

            when (navigationState) {
                is NavigationState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                null -> {
                    Navigator(IncomeSetup()) { navigator ->
                        SlideTransition(navigator)
                    }
                }
                else -> {
                    Navigator(Home()) { navigator ->
                        SlideTransition(navigator)
                    }
                }
            }
        }
    }

}

