package com.andydotdaniel.jajanku.data

import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

val SETTINGS_BUDGET_INCOME = doublePreferencesKey("settings.budget.income")
val SETTINGS_BUDGET_PLAN = stringPreferencesKey("settings.budget.plan")