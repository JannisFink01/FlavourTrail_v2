package com.example.flavourtrail_v2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flavourtrail_v2.data.entity.PlaceTags

@Dao
interface PlaceTagsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(placeTags: PlaceTags)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg placeTags: PlaceTags)

    @Update
    suspend fun update(placeTags: PlaceTags)

    @Delete
    suspend fun delete(placeTags: PlaceTags)

    @Query("SELECT * FROM Place_Tags WHERE place_tag_id = :placeTagId")
    suspend fun getPlaceTagById(placeTagId: Int): PlaceTags?

    @Query("SELECT * FROM Place_Tags")
    suspend fun getAllPlaceTags(): List<PlaceTags>
}