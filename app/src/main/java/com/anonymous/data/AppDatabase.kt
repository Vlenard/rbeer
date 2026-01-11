package com.anonymous.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Beer::class], version = 1, exportSchema = false)
@TypeConverters(BeerTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    // Callback az adatbázis létrehozásakor
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Adatok beszúrása háttér szálon
                            CoroutineScope(Dispatchers.IO).launch {
                                getDatabase(context).beerDao().apply {
                                    val sampleBeers = listOf(
                                        Beer(name = "Soproni Classic", rating = 4, note = "Klasszikus magyar lager, könnyen iható.", type = BeerType.STOUT),
                                        Beer(name = "Mad Scientist IPA", rating = 5, note = "Erősen komlós, gyümölcsös illattal.", type = BeerType.IPA),
                                        Beer(name = "Guinness Draught", rating = 5, note = "Krémes stout, pörkölt malátás íz.", type = BeerType.STOUT),
                                        Beer(name = "Paulaner Hefe-Weißbier", rating = 4, note = "Banános, szegfűszeges búzasör.", type = BeerType.IPA),
                                        Beer(name = "Pilsner Urquell", rating = 3, note = "Kesernyés, tiszta ízvilág.", type = BeerType.LAGER)
                                    )
                                    sampleBeers.forEach { insert(it) }
                                }
                            }
                        }
                    })
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}