package com.andydotdaniel.jajanku.viewModels

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andydotdaniel.jajanku.domain.BudgetPlan
import com.andydotdaniel.jajanku.mocks.BudgetPlanRepositoryMock
import com.andydotdaniel.jajanku.ui.pages.setup.plan.BudgetPlanSetupViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BudgetPlanSetupViewModelTest {

    private val mockBudgetPlanRepository = BudgetPlanRepositoryMock()

    private lateinit var viewModel: BudgetPlanSetupViewModel

    @BeforeTest
    fun setUp() {
        mockBudgetPlanRepository.savedBudgetPlan = BudgetPlan(
            "1",
            50,
            30,
            20
        )
        viewModel = BudgetPlanSetupViewModel(mockBudgetPlanRepository)
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
            70,
            20,
            10
        )
        assertThat(mockBudgetPlanRepository.saveBudgetPlanCalledWithValue).isEqualTo(expectedSavedBudgetPlan)
    }

}