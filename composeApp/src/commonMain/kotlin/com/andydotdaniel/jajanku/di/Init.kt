package com.andydotdaniel.jajanku.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initializeDependencies(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModules)
    }
}
