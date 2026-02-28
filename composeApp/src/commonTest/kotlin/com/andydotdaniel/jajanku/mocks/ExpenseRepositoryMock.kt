package com.andydotdaniel.jajanku.mocks

import com.andydotdaniel.jajanku.data.database.entities.Expense
import com.andydotdaniel.jajanku.data.repository.ExpenseRange
import com.andydotdaniel.jajanku.data.repository.ExpenseRepository

class ExpenseRepositoryMock: ExpenseRepository {

    var expenses = listOf<Expense>()
    override suspend fun getExpenses(timeRange: ExpenseRange): List<Expense> {
        return expenses
    }

    var addExpenseCalledWithArgs: Triple<Double, Int, String?>? = null
    override suspend fun addExpense(
        amount: Double,
        expenseTypeId: Int,
        notes: String?
    ) {
        addExpenseCalledWithArgs = Triple(amount, expenseTypeId, notes)
    }

}