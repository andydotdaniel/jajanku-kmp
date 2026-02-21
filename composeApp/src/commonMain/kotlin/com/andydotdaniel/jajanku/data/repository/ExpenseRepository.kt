package com.andydotdaniel.jajanku.data.repository

import com.andydotdaniel.jajanku.data.database.entities.Expense
import com.andydotdaniel.jajanku.data.database.entities.ExpenseDao
import com.andydotdaniel.jajanku.data.database.entities.ExpenseType
import com.andydotdaniel.jajanku.data.database.entities.ExpenseTypeDao
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant
import kotlin.time.Clock

interface ExpenseTypeRepository {
    suspend fun getExpenseTypes(): List<ExpenseType>
}

enum class ExpenseRange {
    TODAY,
    PAST_WEEK,
    PAST_MONTH
}

interface ExpenseRepository {
    suspend fun getExpenses(timeRange: ExpenseRange): List<Expense>
    suspend fun addExpense(amount: Double, expenseTypeId: Int, notes: String?)
}

class AppExpenseRepository(
    private val expenseTypeDao: ExpenseTypeDao,
    private val expenseDao: ExpenseDao
): ExpenseTypeRepository, ExpenseRepository {

    override suspend fun getExpenseTypes(): List<ExpenseType> {
        return expenseTypeDao.getAll()
    }

    override suspend fun getExpenses(timeRange: ExpenseRange): List<Expense> {
        val now = Clock.System.now().toEpochMilliseconds()

        // TODO: Adjust for timezone
        val offset: Long = when (timeRange) {
            ExpenseRange.TODAY -> { now.mod(86400000L) }

            ExpenseRange.PAST_WEEK -> 1000L * 60 * 60 * 24 * 7

            ExpenseRange.PAST_MONTH -> 1000L * 60 * 60 * 24 * 30
        }

        val startTime: Long = now - offset
        val endTime: Long = now

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