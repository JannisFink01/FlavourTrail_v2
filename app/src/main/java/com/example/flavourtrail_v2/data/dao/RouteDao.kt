package com.example.flovourtrail_v2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.flovourtrail_v2.data.entity.Route

@Dao
interface RouteDao {

    @Insert
    suspend fun insert(route: Route)

    @Insert
    suspend fun insertAll(vararg route: Route)

    @Update
    suspend fun update(route: Route)

    @Delete
    suspend fun delete(route: Route)

    @Query("SELECT * FROM route WHERE route_id = :routeId")
    suspend fun getRouteById(routeId: Int): Route?

    @Query("SELECT * FROM route")
    suspend fun getAllRoutes(): List<Route>
}