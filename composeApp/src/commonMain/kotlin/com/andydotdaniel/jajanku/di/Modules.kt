package com.andydotdaniel.jajanku.di

import com.andydotdaniel.jajanku.ui.pages.setup.income.IncomeSetupViewModel
import com.andydotdaniel.jajanku.ui.pages.setup.plan.BudgetPlanSetupViewModel
import com.andydotdaniel.jajanku.ui.pages.setup.review.ReviewBudgetViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal expect fun dataStoreModule(): Module

val sharedModules = module {
    includes(dataStoreModule())

    viewModelOf(::IncomeSetupViewModel)
    viewModelOf(::BudgetPlanSetupViewModel)
    viewModelOf(::ReviewBudgetViewModel)
}
