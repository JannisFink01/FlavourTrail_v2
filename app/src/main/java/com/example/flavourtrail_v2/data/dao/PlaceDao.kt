package com.example.flovourtrail_v1.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.flovourtrail_v1.database.entity.Place

@Dao
interface PlaceDao {

    @Insert
    suspend fun insert(place: Place)

    @Insert
    suspend fun insertAll(vararg places: Place)

    @Update
    suspend fun update(place: Place)

    @Delete
    suspend fun delete(place: Place)

    @Query("SELECT * FROM Place WHERE place_id = :placeId")
    suspend fun getPlaceById(placeId: Int): Place?

    @Query("SELECT * FROM Place")
    suspend fun getAllPlaces(): List<Place>
}