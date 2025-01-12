package com.example.flovourtrail_v2.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Place_Tags", foreignKeys = [
    ForeignKey(
        entity = Place::class,
        parentColumns = ["place_id"],
        childColumns = ["place_id"],
        onDelete = ForeignKey.CASCADE
    ),
],
    indices = [
        Index(value = ["place_id"])
    ])
data class PlaceTags (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "place_tag_id")
    val placeTagId: Int,
    @ColumnInfo(name = "tag_name") val tagName: String,
    @ColumnInfo(name = "place_id") val placeId: Int,
)