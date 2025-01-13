package com.example.flavourtrail_v2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flavourtrail_v2.data.entity.RouteReview

@Dao
interface RouteReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(routeReview: RouteReview)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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
