package com.andydotdaniel.jajanku.domain

data class Budget(
    val income: Double,
    val budgetPlan: BudgetPlan
)

data class BudgetPlan (
    val needs: Int,
    val wants: Int,
    val savings: Int
)