package com.andydotdaniel.jajanku.viewModels

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andydotdaniel.jajanku.mocks.IncomeRepositoryMock
import com.andydotdaniel.jajanku.ui.pages.setup.income.IncomeSetupViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class IncomeSetupViewModelTest {

    private val mockIncomeRepository = IncomeRepositoryMock()

    private lateinit var viewModel: IncomeSetupViewModel

    @BeforeTest
    fun setUp() {
        mockIncomeRepository.savedIncome = 8500000.0
        viewModel = IncomeSetupViewModel(mockIncomeRepository)
    }


    @Test
    fun testGetIncome() = runTest {
        advanceUntilIdle()
        assertThat(viewModel.uiState.value.income).isEqualTo("8500000.0")
    }
    
    @Test
    fun testUpdateIncome() {
        val newIncomeValue = "7813000"
        viewModel.updateIncome(newIncomeValue)

        assertThat(viewModel.uiState.value.income).isEqualTo(newIncomeValue)
    }

    @Test
    fun testSaveIncome() = runTest {
        val newIncomeValue = "4829000"

        viewModel.updateIncome(newIncomeValue)
        viewModel.submit()
        advanceUntilIdle()

        assertThat(mockIncomeRepository.updateIncomeCalledWithValue).isEqualTo(newIncomeValue.toDouble())
    }

}