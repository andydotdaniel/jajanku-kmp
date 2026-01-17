package com.andydotdaniel.jajanku

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.andydotdaniel.jajanku.ui.pages.setup.income.IncomeSetup
import com.andydotdaniel.jajanku.utils.AppColor

@Composable
fun Navigation() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppColor.Black
    ) {
        Navigator(IncomeSetup()) { navigator ->
            SlideTransition(navigator)
        }
    }
}
