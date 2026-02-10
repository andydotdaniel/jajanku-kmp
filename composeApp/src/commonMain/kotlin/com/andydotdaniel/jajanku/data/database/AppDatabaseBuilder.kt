package com.andydotdaniel.jajanku.data.database

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class AppDatabaseBuilder(private val platformBuilder: PlatformDatabaseBuilder) {

    fun build(): AppDatabase {
        return platformBuilder
            .getDatabaseBuilder()
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

}