package com.anonymous.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerDao {

    /* ────────────────
       INSERT / UPDATE
       ──────────────── */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBeer(beer: Beer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBeers(beers: List<Beer>)

    @Update
    suspend fun updateBeer(beer: Beer)

    @Delete
    suspend fun deleteBeer(beer: Beer)

    @Query("DELETE FROM beers")
    suspend fun deleteAll()

    /* ────────────────
       BASIC QUERIES
       ──────────────── */

    @Query("SELECT * FROM beers ORDER BY name ASC")
    fun getAllBeersFlow(): Flow<List<Beer>>

    @Query("SELECT * FROM beers WHERE id = :id")
    suspend fun getBeerById(id: Long): Beer?

    /* ────────────────
       FILTERING
       ──────────────── */

    @Query("""
        SELECT * FROM beers
        WHERE type = :type
        ORDER BY rating DESC
    """)
    fun getBeersByType(type: BeerType): Flow<List<Beer>>

    @Query("""
        SELECT * FROM beers
        WHERE rating BETWEEN :minRating AND :maxRating
        ORDER BY rating DESC
    """)
    fun getBeersByRatingRange(
        minRating: Int,
        maxRating: Int
    ): Flow<List<Beer>>

    /* ────────────────
       SEARCH
       ──────────────── */

    @Query("""
        SELECT * FROM beers
        WHERE name LIKE '%' || :query || '%'
           OR note LIKE '%' || :query || '%'
        ORDER BY name ASC
    """)
    fun searchBeers(query: String): Flow<List<Beer>>

    /* ────────────────
       STATISTICS
       ──────────────── */

    @Query("SELECT AVG(rating) FROM beers")
    fun getAverageRating(): Flow<Double?>

    @Query("""
        SELECT type, COUNT(*) as count
        FROM beers
        GROUP BY type
    """)
    fun getBeerCountByType(): Flow<List<BeerTypeCount>>

    @Query("""
        SELECT * FROM beers
        ORDER BY rating DESC
        LIMIT 1
    """)
    suspend fun getTopRatedBeer(): Beer?

    /* ────────────────
       ADVANCED
       ──────────────── */

    @Query("""
        UPDATE beers
        SET rating = :rating
        WHERE id = :beerId
          AND :rating BETWEEN 1 AND 5
    """)
    suspend fun updateRatingSafely(
        beerId: Long,
        rating: Int
    ): Int
}
