package com.andydotdaniel.jajanku.viewModels

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andydotdaniel.jajanku.domain.BudgetPlan
import com.andydotdaniel.jajanku.mocks.BudgetPlanRepositoryMock
import com.andydotdaniel.jajanku.ui.screens.setup.plan.BudgetPlanSetupScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BudgetPlanSetupViewModelTest {

    private val mockBudgetPlanRepository = BudgetPlanRepositoryMock()

    private lateinit var viewModel: BudgetPlanSetupScreenViewModel

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        mockBudgetPlanRepository.savedBudgetPlan = BudgetPlan(
            "1",
            0.5f,
            0.3f,
            0.2f
        )
        viewModel = BudgetPlanSetupScreenViewModel(mockBudgetPlanRepository)
    }

    @Test
    fun testGetBudgetPlan() = runTest {
        advanceUntilIdle()
        assertThat(viewModel.uiState.value.selectedBudgetPlan).isEqualTo("1")
    }

    @Test
    fun testSaveBudgetPlan() = runTest {
        val selectedBudgetPlanId = "1"
        viewModel.selectBudgetPlan(selectedBudgetPlanId)
        viewModel.submit()
        advanceUntilIdle()

        val expectedSavedBudgetPlan = BudgetPlan(
            selectedBudgetPlanId,
            0.70f,
            0.20f,
            0.10f
        )
        assertThat(mockBudgetPlanRepository.saveBudgetPlanCalledWithValue).isEqualTo(expectedSavedBudgetPlan)
    }

}