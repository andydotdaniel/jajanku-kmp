package com.andydotdaniel.jajanku.viewModels

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andydotdaniel.jajanku.domain.BudgetPlan
import com.andydotdaniel.jajanku.ui.screens.setup.review.ReviewBudgetScreenEvent
import com.andydotdaniel.jajanku.ui.screens.setup.review.ReviewBudgetScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ReviewBudgetScreenViewModelTest {

    private lateinit var viewModel: ReviewBudgetScreenViewModel

    private val initiatedIncome: Double = 2500000.0
    private val initiatedBudgetPlan: BudgetPlan
        get() {
            return BudgetPlan(
                "1",
                0.5f,
                0.3f,
                0.2f
            )
        }

    private val uiEvents = Channel<ReviewBudgetScreenEvent>(capacity = 1)

    @BeforeTest
    fun setUp() {
        viewModel = ReviewBudgetScreenViewModel(initiatedIncome, initiatedBudgetPlan, uiEvents)
    }

    @Test
    fun testSelectBudgetView() {
        val index = 2
        viewModel.selectBudgetView(index)

        assertThat(viewModel.uiState.value.selectedBudgetView).isEqualTo(index)
    }

    @Test
    fun testOnSaveButtonTapped() = runTest {
        viewModel.onSaveBudgetTapped()

        assertThat(uiEvents.receive() is ReviewBudgetScreenEvent.NavigateToHome).isEqualTo(true)
    }

}