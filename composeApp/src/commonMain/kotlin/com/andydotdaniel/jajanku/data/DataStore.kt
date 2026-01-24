package com.andydotdaniel.jajanku.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

interface PreferencesDataStore {
    val preferences: DataStore<Preferences>
}

expect class AppDataStore: PreferencesDataStore {
    override val preferences: DataStore<Preferences>
}

/**
 *   Gets the singleton DataStore instance, creating it if necessary.
 */
fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

internal const val dataStoreFileName = "app.preferences_pb"