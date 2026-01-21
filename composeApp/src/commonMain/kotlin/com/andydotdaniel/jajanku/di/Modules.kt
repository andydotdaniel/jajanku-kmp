package com.andydotdaniel.jajanku.di

import com.andydotdaniel.jajanku.ui.pages.setup.income.IncomeSetupViewModel
import com.andydotdaniel.jajanku.ui.pages.setup.plan.BudgetPlanSetupViewModel
import com.andydotdaniel.jajanku.ui.pages.setup.review.ReviewBudgetViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModules = module {
    viewModelOf(::IncomeSetupViewModel)
    viewModelOf(::BudgetPlanSetupViewModel)
    viewModelOf(::ReviewBudgetViewModel)
}