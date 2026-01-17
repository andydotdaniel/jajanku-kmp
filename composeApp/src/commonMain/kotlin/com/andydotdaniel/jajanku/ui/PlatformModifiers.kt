package com.andydotdaniel.jajanku.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Declares an expected function that will provide platform-specific
 * horizontal padding.
 */
@Composable
expect fun Modifier.platformSafeContentPadding(): Modifier
