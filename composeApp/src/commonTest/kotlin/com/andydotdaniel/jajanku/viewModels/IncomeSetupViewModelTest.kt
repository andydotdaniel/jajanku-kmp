package com.andydotdaniel.jajanku.viewModels

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andydotdaniel.jajanku.mocks.IncomeRepositoryMock
import com.andydotdaniel.jajanku.ui.screens.setup.income.IncomeSetupViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class IncomeSetupViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var mockIncomeRepository: IncomeRepositoryMock

    private lateinit var viewModel: IncomeSetupViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        mockIncomeRepository = IncomeRepositoryMock()
        mockIncomeRepository.savedIncome = 8500000.0
        viewModel = IncomeSetupViewModel(mockIncomeRepository)
    }

    @AfterTest
    fun tearDown() {
        // 3. After each test, clean up the main dispatcher to avoid affecting other tests
        Dispatchers.resetMain()
    }

    @Test
    fun testGetIncome() = runTest(testDispatcher) {
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
    fun testSaveIncome() = runTest(testDispatcher) {
        val newIncomeValue = "4829000"

        viewModel.updateIncome(newIncomeValue)
        viewModel.submit()
        advanceUntilIdle()

        assertThat(mockIncomeRepository.updateIncomeCalledWithValue).isEqualTo(newIncomeValue.toDouble())
    }

}