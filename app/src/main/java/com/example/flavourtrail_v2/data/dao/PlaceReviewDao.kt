package com.example.flavourtrail_v2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.flavourtrail_v2.data.entity.PlaceReview
import com.example.flavourtrail_v2.data.entity.PlaceReviewWithDetails

@Dao
interface PlaceReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaceReview(placeReview: PlaceReview): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg placeReviews: PlaceReview)

    @Update
    suspend fun updatePlaceReview(placeReview: PlaceReview)

    @Query("DELETE FROM PlaceReview WHERE review_id = :reviewId")
    suspend fun deletePlaceReview(reviewId: Int)

    @Query("SELECT * FROM PlaceReview WHERE review_id = :reviewId")
    suspend fun getPlaceReviewById(reviewId: Int): PlaceReview?

    @Query("SELECT * FROM PlaceReview")
    suspend fun getAllPlaceReviews(): List<PlaceReview>

    @Query("SELECT * FROM PlaceReview WHERE place_id = :placeId")
    suspend fun getPlaceReviewsByPlaceId(placeId: Int): List<PlaceReview>

    @Query("SELECT * FROM PlaceReview WHERE user_id = :userId")
    suspend fun getPlaceReviewsByUserId(userId: Int): List<PlaceReview>

    @Transaction
    @Query(
        """
        SELECT * FROM PlaceReview
        INNER JOIN User ON PlaceReview.user_id = User.user_id
        INNER JOIN Place ON PlaceReview.place_id = Place.place_id
        WHERE PlaceReview.place_Id = :placeId
    """
    )
    suspend fun getPlaceReviewsWithDetailsByPlaceId(placeId: Int): List<PlaceReviewWithDetails>
}