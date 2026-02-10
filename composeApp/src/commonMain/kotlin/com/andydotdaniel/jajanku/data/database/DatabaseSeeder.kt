package com.andydotdaniel.jajanku.data.database

import com.andydotdaniel.jajanku.data.repository.DatabaseInitializerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DatabaseSeeder(
    private val dbInitializerRepository: DatabaseInitializerRepository,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
) {

    fun seedDatabaseIfNeeded() {
        coroutineScope.launch {
            if (!dbInitializerRepository.isDatabaseSeeded()) {
                dbInitializerRepository.seedCategories()
                dbInitializerRepository.setDatabaseSeeded()
            }
        }
    }

}