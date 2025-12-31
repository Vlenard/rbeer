package com.anonymous.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Beer::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(BeerTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao
}
