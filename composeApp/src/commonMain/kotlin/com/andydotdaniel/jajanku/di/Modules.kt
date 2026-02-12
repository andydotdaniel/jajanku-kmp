package com.andydotdaniel.jajanku.di

import com.andydotdaniel.jajanku.Launcher
import com.andydotdaniel.jajanku.data.database.AppDatabase
import com.andydotdaniel.jajanku.data.database.AppDatabaseBuilder
import com.andydotdaniel.jajanku.data.database.DatabaseSeeder
import com.andydotdaniel.jajanku.data.database.entities.AccountedBudgetDao
import com.andydotdaniel.jajanku.data.database.entities.ExpenseDao
import com.andydotdaniel.jajanku.data.database.entities.ExpenseTypeDao
import com.andydotdaniel.jajanku.data.repository.AppBudgetPlanRepository
import com.andydotdaniel.jajanku.data.repository.AppDatabaseInitializerRepository
import com.andydotdaniel.jajanku.data.repository.AppExpenseRepository
import com.andydotdaniel.jajanku.data.repository.AppIncomeRepository
import com.andydotdaniel.jajanku.data.repository.BudgetPlanRepository
import com.andydotdaniel.jajanku.data.repository.DatabaseInitializerRepository
import com.andydotdaniel.jajanku.data.repository.ExpenseRepository
import com.andydotdaniel.jajanku.data.repository.ExpenseTypeRepository
import com.andydotdaniel.jajanku.data.repository.IncomeRepository
import com.andydotdaniel.jajanku.ui.pages.expense.ExpenseInputScreenViewModel
import com.andydotdaniel.jajanku.ui.pages.home.HomeViewModel
import com.andydotdaniel.jajanku.ui.pages.setup.income.IncomeSetupViewModel
import com.andydotdaniel.jajanku.ui.pages.setup.plan.BudgetPlanSetupViewModel
import com.andydotdaniel.jajanku.ui.pages.setup.review.ReviewBudgetViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

// DataStore here refers our key-pair data store for storing user defaults and preferences
// Database refers to our app's main database that stores our expenses and other core data
internal expect fun dataStoreModule(): Module
internal expect fun databaseBuilderModule(): Module

internal fun repositoryModule(): Module = module {
    single<DatabaseInitializerRepository> { AppDatabaseInitializerRepository(get(), get()) }

    single<IncomeRepository> { AppIncomeRepository(get()) }
    single<BudgetPlanRepository> { AppBudgetPlanRepository(get()) }

    single<AppExpenseRepository> { AppExpenseRepository(get(), get()) }
    single<ExpenseTypeRepository> { get<AppExpenseRepository>() }
    single<ExpenseRepository> { get<AppExpenseRepository>() }
}
internal fun databaseModule(): Module = module {
    single<DatabaseSeeder> { DatabaseSeeder(get()) }
    single<AppDatabase> { AppDatabaseBuilder(get()).build() }

    // Provide the YourDao instance by getting it from the AppDatabase
    single<ExpenseTypeDao> { get<AppDatabase>().expenseTypeDao() }
    single<ExpenseDao> { get<AppDatabase>().expenseDao() }
    single<AccountedBudgetDao> { get<AppDatabase>().accountedBudgetDao() }
}

val sharedModules = module {
    includes(dataStoreModule())

    includes(databaseBuilderModule())
    includes(databaseModule())

    includes(repositoryModule())

    single<Launcher> { Launcher(get(), get()) }

    viewModelOf(::IncomeSetupViewModel)
    viewModelOf(::BudgetPlanSetupViewModel)
    viewModel { params ->
        ReviewBudgetViewModel(
            income = params.get(),
            budgetPlan = params.get()
        )
    }
    viewModelOf(::HomeViewModel)
    viewModelOf(::ExpenseInputScreenViewModel)
}
