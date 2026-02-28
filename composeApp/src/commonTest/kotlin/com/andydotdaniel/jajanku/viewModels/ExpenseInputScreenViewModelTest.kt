package com.andydotdaniel.jajanku.viewModels

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andydotdaniel.jajanku.mocks.ExpenseRepositoryMock
import com.andydotdaniel.jajanku.mocks.ExpenseTypeRepositoryMock
import com.andydotdaniel.jajanku.ui.screens.expense.ExpenseInputScreenEvent
import com.andydotdaniel.jajanku.ui.screens.expense.ExpenseInputScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExpenseInputScreenViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ExpenseInputScreenViewModel

    private lateinit var expenseTypeRepository: ExpenseTypeRepositoryMock
    private lateinit var expenseRepository: ExpenseRepositoryMock

    private val uiEvents = Channel<ExpenseInputScreenEvent>(capacity = 1)

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        expenseTypeRepository = ExpenseTypeRepositoryMock()
        expenseRepository = ExpenseRepositoryMock()

        viewModel = ExpenseInputScreenViewModel(
            expenseTypeRepository,
            expenseRepository,
            uiEvents,
            testDispatcher
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testUpdateExpenseAmount() {
        val input = "5628990"
        viewModel.updateExpenseAmount(input)

        assertThat(viewModel.uiState.value.expenseAmount).isEqualTo("5628990")
    }

    @Test
    fun testUpdateExpenseAmount_invalidInput() {
        val input = "dasfs"
        viewModel.updateExpenseAmount(input)

        assertThat(viewModel.uiState.value.expenseAmount).isEqualTo("")
    }

    @Test
    fun testUpdateNotes() {
        val notes = "Some notes"
        viewModel.updateNotes(notes)

        assertThat(viewModel.uiState.value.notes).isEqualTo(notes)
    }

    @Test
    fun testShowExpenseTypeSheet_showSheet() {
        viewModel.showExpenseTypeSheet(true)
        assertThat(viewModel.uiState.value.isExpenseTypeSheetOpen).isEqualTo(true)
    }

    @Test
    fun testShowExpenseTypeSheet_hideSheet() {
        viewModel.showExpenseTypeSheet(false)
        assertThat(viewModel.uiState.value.isExpenseTypeSheetOpen).isEqualTo(false)
    }

    @Test
    fun testOnExpenseTypeSelected() {
        val selectedExpenseTypeId = 2
        viewModel.onExpenseTypeSelected(selectedExpenseTypeId)

        assertThat(viewModel.uiState.value.selectedExpenseTypeId).isEqualTo(selectedExpenseTypeId)
    }

    @Test
    fun testOnSaveButtonPressed() = runTest(testDispatcher) {
        // TODO: Implement onSaveButtonPressed test
    }

}