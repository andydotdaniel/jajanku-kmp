package com.andydotdaniel.jajanku.di

import com.andydotdaniel.jajanku.data.AppDataStore
import com.andydotdaniel.jajanku.data.database.PlatformDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun dataStoreModule(): Module = module {
    single { AppDataStore(get()) }
}

internal actual fun databaseBuilderModule(): Module = module {
    single { PlatformDatabaseBuilder(get()) }
}