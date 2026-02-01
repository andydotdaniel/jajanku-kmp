package com.andydotdaniel.jajanku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.andydotdaniel.jajanku.data.repository.BudgetPlanRepository
import org.koin.android.ext.android.inject

class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val budgetPlanRepository: BudgetPlanRepository by inject()
        val launcher = Launcher(budgetPlanRepository)

        setContent {
            launcher.Navigation()
        }
    }
}