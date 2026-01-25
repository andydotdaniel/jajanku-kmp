package com.andydotdaniel.jajanku.di

import com.andydotdaniel.jajanku.data.repository.AppBudgetPlanRepository
import com.andydotdaniel.jajanku.data.repository.AppIncomeRepository
import com.andydotdaniel.jajanku.data.repository.BudgetPlanRepository
import com.andydotdaniel.jajanku.data.repository.IncomeRepository
import com.andydotdaniel.jajanku.ui.pages.setup.income.IncomeSetupViewModel
import com.andydotdaniel.jajanku.ui.pages.setup.plan.BudgetPlanSetupViewModel
import com.andydotdaniel.jajanku.ui.pages.setup.review.ReviewBudgetViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal expect fun dataStoreModule(): Module

internal fun repositoryModule(): Module = module {
    single<IncomeRepository> { AppIncomeRepository(get()) }
    single<BudgetPlanRepository> { AppBudgetPlanRepository(get()) }
}

val sharedModules = module {
    includes(dataStoreModule())
    includes(repositoryModule())

    viewModelOf(::IncomeSetupViewModel)
    viewModelOf(::BudgetPlanSetupViewModel)
    viewModelOf(::ReviewBudgetViewModel)
}
