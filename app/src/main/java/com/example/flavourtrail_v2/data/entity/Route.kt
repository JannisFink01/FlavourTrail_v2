package com.example.flovourtrail_v1.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "route")
data class Route(
    @PrimaryKey val route_id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "route_type") val routeType: String,
    @ColumnInfo(name = "avg_rating") val avgRating: Double,
    @ColumnInfo(name = "shared_count") val sharedCount: Int,
    )