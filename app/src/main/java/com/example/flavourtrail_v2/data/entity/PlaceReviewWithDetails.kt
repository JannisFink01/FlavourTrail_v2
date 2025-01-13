package com.example.flavourtrail_v2.data.entity

import androidx.room.Embedded
import androidx.room.Relation

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