package com.example.flovourtrail_v1.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.flavourtrail_v2.data.entity.PlaceReviewWithDetails
import com.example.flovourtrail_v1.database.entity.Place
import com.example.flovourtrail_v1.database.entity.PlaceReview
import com.example.flovourtrail_v1.database.entity.RouteReview
import com.example.flovourtrail_v1.database.entity.User

@Dao
interface RouteReviewDao {

    @Insert
    suspend fun insert(routeReview: RouteReview)

    @Insert
    suspend fun insertAll(vararg routeReviews: RouteReview)

    @Update
    suspend fun update(routeReview: RouteReview)

    @Delete
    suspend fun delete(routeReview: RouteReview)

    @Query("SELECT * FROM routes_reviews WHERE review_id = :reviewId")
    suspend fun getRouteReviewById(reviewId: Int): RouteReview?

    @Query("SELECT * FROM routes_reviews")
    suspend fun getAllRouteReviews(): List<RouteReview>

}
