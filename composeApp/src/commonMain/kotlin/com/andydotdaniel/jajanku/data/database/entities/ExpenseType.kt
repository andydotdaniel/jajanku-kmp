package com.andydotdaniel.jajanku.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "expense_type")
data class ExpenseType(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo val titles: String,
    @ColumnInfo val icon: String,
    @ColumnInfo val active: Boolean
)

@Dao
interface ExpenseTypeDao {
    @Query("SELECT * FROM expense_type")
    suspend fun getAll(): List<ExpenseType>

    @Query("SELECT * FROM expense_type WHERE uid = :id")
    suspend fun findById(id: Int): ExpenseType?

    @Insert
    fun insertAll(vararg expenseType: ExpenseType)
}