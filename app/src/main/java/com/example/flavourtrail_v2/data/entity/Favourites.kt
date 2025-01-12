package com.example.flovourtrail_v2.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favourites",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Route::class,
            parentColumns = ["route_id"],
            childColumns = ["route_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["route_id"])
    ])
data class Favourites (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "favourite_id")
    val favouriteId: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "route_id") val routeId: Int,
)