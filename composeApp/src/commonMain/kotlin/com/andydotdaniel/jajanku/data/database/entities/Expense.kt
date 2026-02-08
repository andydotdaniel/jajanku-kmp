package com.andydotdaniel.jajanku.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ExpenseType::class,
            parentColumns = ["uid"],
            childColumns = ["type_id"],
            onUpdate = ForeignKey.RESTRICT,
            onDelete = ForeignKey.RESTRICT
        ),
    ],
    indices = [Index(value = ["type_id"])]
)

data class Expense(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo val amount: Float,
    @ColumnInfo(name = "type_id") val type: Int,
    @ColumnInfo val notes: String?,
    @ColumnInfo val timestamp: Long
)

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense WHERE timestamp >= :startTime AND timestamp < :endTime")
    suspend fun findByTimeRange(startTime: Long, endTime: Long): List<Expense>

    @Query("DELETE FROM expense WHERE uid = :id")
    suspend fun deleteById(id: Int)

    @Insert
    suspend fun insert(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)
}