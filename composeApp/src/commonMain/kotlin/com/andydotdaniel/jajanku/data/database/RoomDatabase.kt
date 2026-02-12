package com.andydotdaniel.jajanku.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.andydotdaniel.jajanku.data.database.entities.AccountedBudget
import com.andydotdaniel.jajanku.data.database.entities.AccountedBudgetDao
import com.andydotdaniel.jajanku.data.database.entities.Expense
import com.andydotdaniel.jajanku.data.database.entities.ExpenseDao
import com.andydotdaniel.jajanku.data.database.entities.ExpenseType
import com.andydotdaniel.jajanku.data.database.entities.ExpenseTypeDao

@Database(entities = [
    AccountedBudget::class,
    ExpenseType::class,
    Expense::class
], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountedBudgetDao(): AccountedBudgetDao
    abstract fun expenseTypeDao(): ExpenseTypeDao
    abstract fun expenseDao(): ExpenseDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}