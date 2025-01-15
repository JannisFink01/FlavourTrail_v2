package com.example.flavourtrail_v2.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "PlaceReview",
    foreignKeys = [
        ForeignKey(
            entity = Place::class,
            parentColumns = ["place_id"],
            childColumns = ["place_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["place_id"]),
        Index(value = ["user_id"])
    ])
data class PlaceReview (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "review_id")
    val reviewId: Int = 0,
    @ColumnInfo(name = "place_id") val placeId: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "comment") val comment: String,
    @ColumnInfo(name = "date") val date: Date,
)