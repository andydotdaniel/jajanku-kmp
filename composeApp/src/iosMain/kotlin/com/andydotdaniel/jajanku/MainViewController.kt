package com.andydotdaniel.jajanku

import androidx.compose.ui.window.ComposeUIViewController
import com.andydotdaniel.jajanku.di.initializeDependencies

fun MainViewController() = ComposeUIViewController(
    configure = {
        initializeDependencies()
    }
) {
    Navigation()
}