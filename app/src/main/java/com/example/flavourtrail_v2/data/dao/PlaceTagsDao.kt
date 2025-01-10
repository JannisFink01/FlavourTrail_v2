package com.example.flovourtrail_v1.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.flovourtrail_v1.database.entity.PlaceTags

@Dao
interface PlaceTagsDao {

    @Insert
    suspend fun insert(placeTags: PlaceTags)

    @Insert
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