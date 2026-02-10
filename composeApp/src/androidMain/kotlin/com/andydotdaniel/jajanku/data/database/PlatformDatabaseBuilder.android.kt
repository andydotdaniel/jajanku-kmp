package com.andydotdaniel.jajanku.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class PlatformDatabaseBuilder(private val context: Context) {

    actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath("jajanku.db")
        return Room.databaseBuilder<AppDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }

}