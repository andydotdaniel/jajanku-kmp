package com.andydotdaniel.jajanku

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
import com.andydotdaniel.jajanku.data.database.DatabaseSeeder
import com.andydotdaniel.jajanku.data.repository.BudgetPlanRepository
import com.andydotdaniel.jajanku.ui.navigation.ModalScreen
import com.andydotdaniel.jajanku.ui.screens.home.Home
import com.andydotdaniel.jajanku.ui.screens.setup.income.IncomeSetup
import com.andydotdaniel.jajanku.utils.AppColor
import kotlinx.coroutines.flow.take

sealed interface NavigationState {
    object Loading : NavigationState

}

class Launcher(
    databaseSeeder: DatabaseSeeder,
    private val budgetRepository: BudgetPlanRepository
) {

    init {
        databaseSeeder.seedDatabaseIfNeeded()
    }

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
                        NavigationTransition(navigator)
                    }
                }
                else -> {
                    Navigator(Home()) { navigator ->
                        NavigationTransition(navigator)
                    }
                }
            }
        }
    }

    @Composable
    private fun NavigationTransition(navigator: Navigator) {
        AnimatedContent(
            targetState = navigator.lastItem,
            transitionSpec = {
                // The screen we are navigating to
                val toScreen = targetState
                // The screen we are navigating from
                val fromScreen = initialState

                // Check if either screen is a modal to apply the vertical transition
                val isModal = toScreen is ModalScreen || fromScreen is ModalScreen

                // This logic reliably determines if we are pushing or popping a screen
                val isPush = fromScreen.let { screen ->
                    navigator.items.any { it.key == screen.key }
                }

                if (isModal) {
                    if (isPush) {
                        // Pushing a modal screen: slides in from bottom
                        slideInVertically(initialOffsetY = { it }) + fadeIn() togetherWith
                                fadeOut()
                    } else {
                        // Popping a modal screen: slides out to bottom
                        fadeIn() togetherWith
                                slideOutVertically(targetOffsetY = { it }) + fadeOut()
                    }
                } else {
                    if (isPush) {
                        // Default push transition: slides in from right
                        slideInHorizontally(initialOffsetX = { it }) + fadeIn() togetherWith
                                slideOutHorizontally(targetOffsetX = { -it / 4 }) + fadeOut()
                    } else {
                        // Default pop transition: slides out to right
                        slideInHorizontally(initialOffsetX = { -it / 4 }) + fadeIn() togetherWith
                                slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
                    }
                }
            }
        ) { screen ->
            // Renders the current screen's content
            screen.Content()
        }
    }
}

