package com.example.flightsearchapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {

    @Query("SELECT * FROM airport WHERE name LIKE '%' || :search || '%' OR iata_code LIKE '%' || :search || '%' ORDER BY passengers")
    fun getAirports(search: String): Flow<List<airport>>


    @Query("SELECT * FROM airport WHERE id = :id")
    fun getResult(id: Int): Flow<airport>

    @Query("SELECT * FROM airport WHERE NOT id = :id")
    fun getDestinations(id: Int): Flow<List<airport>>



    @Query("SELECT * FROM favorite")
    fun getFavorites(): Flow<List<favorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFav(fav: favorite)

}