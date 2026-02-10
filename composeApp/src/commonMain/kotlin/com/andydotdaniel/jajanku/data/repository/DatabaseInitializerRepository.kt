package com.andydotdaniel.jajanku.data.repository

import com.andydotdaniel.jajanku.data.AppDataStore
import com.andydotdaniel.jajanku.data.DATABASE_IS_SEEDED
import com.andydotdaniel.jajanku.data.database.entities.ExpenseType
import com.andydotdaniel.jajanku.data.database.entities.ExpenseTypeDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface DatabaseInitializerRepository {
    suspend fun isDatabaseSeeded(): Boolean
    suspend fun setDatabaseSeeded()

    suspend fun seedCategories()
}

class AppDatabaseInitializerRepository(
    private val dataStore: AppDataStore,
    private val expenseTypeDao: ExpenseTypeDao
): DatabaseInitializerRepository {

    override suspend fun isDatabaseSeeded(): Boolean {
        return dataStore.preferences.data.map { preferences ->
            preferences[DATABASE_IS_SEEDED] ?: false
        }.first()
    }

    override suspend fun setDatabaseSeeded() {
        dataStore.preferences.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[DATABASE_IS_SEEDED] = true
            }
        }
    }

    override suspend fun seedCategories() {
        val defaultExpenseTypes = listOf(
            ExpenseType(uid = 0, titles = "{ \"en\": \"Food\", \"id\": \"Makanan\" }", icon = "\uD83C\uDF7D\uFE0F", active = true),
            ExpenseType(uid = 0, titles = "{ \"en\": \"Drink\", \"id\": \"Minuman\" }", icon = "\uD83E\uDDCB", active = true),
            ExpenseType(uid = 0, titles = "{ \"en\": \"Transport\", \"id\": \"Transportasi\" }", icon = "\uD83D\uDE86", active = true),
            ExpenseType(uid = 0, titles = "{ \"en\": \"Shopping\", \"id\": \"Belanja\" }", icon = "\uD83D\uDECD\uFE0F", active = true),
            ExpenseType(uid = 0, titles = "{ \"en\": \"Leisure\", \"id\": \"Hura-Hura\" }", icon = "\uD83C\uDF34", active = true),
            ExpenseType(uid = 0, titles = "{ \"en\": \"Others\", \"id\": \"Lain-Lain\" }", icon = "\uD83D\uDCB0", active = true)
        )

        expenseTypeDao.insertAll(*defaultExpenseTypes.toTypedArray())
    }

}