package com.example.flovourtrail_v2.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.flovourtrail_v1.database.entity.Place
import com.example.flovourtrail_v1.database.entity.PlaceReview
import com.example.flovourtrail_v1.database.entity.User

data class PlaceReviewWithDetails(
    @Embedded val placeReview: PlaceReview,

    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id"
    )
    val user: User,

    @Relation(
        parentColumn = "place_id",
        entityColumn = "place_id"
    )
    val place: Place
)