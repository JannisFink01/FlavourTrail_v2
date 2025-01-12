package com.example.flovourtrail_v2.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Route_Place",
    foreignKeys = [
        ForeignKey(
            entity = Route::class,
            parentColumns = ["route_id"],
            childColumns = ["route_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Place::class,
            parentColumns = ["place_id"],
            childColumns = ["place_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["route_id"]),
        Index(value = ["place_id"])
    ])
data class RoutePlace (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "route_place_id")
    val routePlaceId: Int,
    @ColumnInfo(name = "route_id") val routeId: Int,
    @ColumnInfo(name = "place_id") val placeId: Int,
)