package com.andydotdaniel.jajanku.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual class AppDataStore(private val context: Context) : PreferencesDataStore {

    actual override val preferences: DataStore<Preferences> = createDataStore(
        producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
    )

}