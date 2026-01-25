package com.anonymous.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerDao {
    // CREATE: Új sör hozzáadása
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(beer: Beer): Long

    // READ: Minden sör lekérdezése
    @Query("SELECT * FROM beers")
    suspend fun getAllBeers(): List<Beer>

    // READ: Egy adott sör lekérdezése ID alapján
    @Query("SELECT * FROM beers WHERE id = :id")
    suspend fun getBeerById(id: Long): Beer

    // UPDATE: Egy sör frissítése
    @Update
    suspend fun update(beer: Beer)

    // DELETE: Egy sör törlése
    @Delete
    suspend fun delete(beer: Beer)

    // DELETE: Összes sör törlése (opcionális)
    @Query("DELETE FROM beers")
    suspend fun deleteAll()
}
