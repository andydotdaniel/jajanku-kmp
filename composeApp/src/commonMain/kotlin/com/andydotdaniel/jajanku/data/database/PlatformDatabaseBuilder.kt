package com.andydotdaniel.jajanku.data.database

import androidx.room.RoomDatabase

expect class PlatformDatabaseBuilder {
    fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
}