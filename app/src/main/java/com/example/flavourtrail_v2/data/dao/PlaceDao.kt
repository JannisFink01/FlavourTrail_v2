package com.example.flavourtrail_v2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flavourtrail_v2.data.entity.Place

@Dao
interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: Place)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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