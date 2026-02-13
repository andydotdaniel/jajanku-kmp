package com.andydotdaniel.jajanku.data.repository

import com.andydotdaniel.jajanku.data.database.entities.Expense
import com.andydotdaniel.jajanku.data.database.entities.ExpenseDao
import com.andydotdaniel.jajanku.data.database.entities.ExpenseType
import com.andydotdaniel.jajanku.data.database.entities.ExpenseTypeDao
import kotlin.time.Clock

interface ExpenseTypeRepository {
    suspend fun getExpenseTypes(): List<ExpenseType>
}

interface ExpenseRepository {
    suspend fun getExpenses(): List<Expense>
    suspend fun addExpense(amount: Double, expenseTypeId: Int, notes: String?)
}

class AppExpenseRepository(
    private val expenseTypeDao: ExpenseTypeDao,
    private val expenseDao: ExpenseDao
): ExpenseTypeRepository, ExpenseRepository {

    override suspend fun getExpenseTypes(): List<ExpenseType> {
        return expenseTypeDao.getAll()
    }

    override suspend fun getExpenses(): List<Expense> {
        val startTime: Long = Clock.System.now().toEpochMilliseconds() - 1000 * 60 * 60 * 24 * 30 // Past month
        val endTime: Long = Clock.System.now().toEpochMilliseconds()

        return expenseDao.findByTimeRange(startTime, endTime)
    }

    override suspend fun addExpense(amount: Double, expenseTypeId: Int, notes: String?) {
        expenseDao.insert(
            Expense(
                0,
                amount,
                expenseTypeId,
                notes,
                Clock.System.now().toEpochMilliseconds()
            )
        )
    }

}