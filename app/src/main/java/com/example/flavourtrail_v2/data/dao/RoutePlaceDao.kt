package com.example.flovourtrail_v1.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.flovourtrail_v1.database.entity.RoutePlace

@Dao
interface RoutePlaceDao {

    @Insert
    suspend fun insert(routePlace: RoutePlace)

    @Insert
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