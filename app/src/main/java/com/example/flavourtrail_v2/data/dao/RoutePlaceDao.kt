package com.example.flavourtrail_v2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flavourtrail_v2.data.entity.RoutePlace

@Dao
interface RoutePlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(routePlace: RoutePlace)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg routePlaces: RoutePlace)

    @Update
    suspend fun update(routePlace: RoutePlace)

    @Delete
    suspend fun delete(routePlace: RoutePlace)

    @Query("SELECT * FROM Route_Place WHERE route_place_id = :routePlaceId")
    suspend fun getRoutePlaceById(routePlaceId: Int): RoutePlace?

    @Query("SELECT * FROM Route_Place")
    suspend fun getAllRoutePlaces(): List<RoutePlace>
}