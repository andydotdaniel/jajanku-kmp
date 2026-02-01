package com.andydotdaniel.jajanku

import androidx.compose.ui.window.ComposeUIViewController
import com.andydotdaniel.jajanku.di.initializeDependencies
import org.koin.compose.koinInject

fun MainViewController() = ComposeUIViewController(
    configure = {
        initializeDependencies()
    }
) {
    val launcher = koinInject<Launcher>()
    launcher.Navigation()
}