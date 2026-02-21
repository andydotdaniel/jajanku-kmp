package com.andydotdaniel.jajanku.domain

data class Budget(
    val income: Double,
    val budgetPlan: BudgetPlan
) {
    val spendingBudgetAmount: Double
        get() = needsAmount + wantsAmount

    val needsAmount: Double
        get() = income * budgetPlan.needs

    val wantsAmount: Double
        get() = income * budgetPlan.wants

    val savingsAmount: Double
        get() = income * budgetPlan.savings
}

data class BudgetPlan (
    val id: String,
    val needs: Float,
    val wants: Float,
    val savings: Float
)