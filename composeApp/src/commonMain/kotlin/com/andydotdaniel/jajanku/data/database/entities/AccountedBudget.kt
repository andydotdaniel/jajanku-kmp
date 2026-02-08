package com.andydotdaniel.jajanku.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.datetime.LocalDate

@Entity(tableName = "accounted_budget")
data class AccountedBudget(
    @PrimaryKey val date: String,
    @ColumnInfo(name = "budget") val budget: Float
)

@Dao
interface AccountedBudgetDao {
    @Query("SELECT * FROM accounted_budget WHERE date >= :startDate AND date < :endDate")
    fun findByDateRange(startDate: String, endDate: String): List<AccountedBudget>

    @Insert
    fun insertAll(vararg accountedBudgets: AccountedBudget)

    @Update
    fun updateAccountedBudgets(vararg accountedBudgets: AccountedBudget)
}