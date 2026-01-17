package com.andydotdaniel.jajanku.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun Modifier.platformSafeContentPadding(): Modifier {
    return this.then(Modifier.windowInsetsPadding(WindowInsets.safeContent))
}