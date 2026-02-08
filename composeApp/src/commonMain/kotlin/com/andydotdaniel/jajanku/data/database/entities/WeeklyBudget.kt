package com.andydotdaniel.jajanku.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Entity(tableName = "weekly_budget")
data class WeeklyBudget(
    @PrimaryKey @ColumnInfo(name = "day_of_the_week") val dayOfTheWeek: Int,
    @ColumnInfo val budget: Float
)

@Dao
interface WeeklyBudgetDao {
    @Query("SELECT * FROM weekly_budget")
    suspend fun getAll(): List<WeeklyBudget>

    @Query("SELECT * FROM weekly_budget WHERE day_of_the_week = :day LIMIT 1")
    suspend fun findByDay(day: Int): WeeklyBudget

    @Upsert
    suspend fun upsertAll(budgets: List<WeeklyBudget>)

    @Update
    suspend fun updateBudgets(vararg budgets: WeeklyBudget)
}