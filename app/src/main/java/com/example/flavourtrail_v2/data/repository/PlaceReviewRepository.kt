package com.example.flavourtrail_v2.data.repository


import com.example.flovourtrail_v2.data.dao.PlaceReviewDao
import com.example.flovourtrail_v2.data.entity.PlaceReview
import com.example.flovourtrail_v2.data.entity.PlaceReviewWithDetails

class PlaceReviewRepository(private val placeReviewDao: PlaceReviewDao) {

    suspend fun insertPlaceReview(placeReview: PlaceReview) {
        placeReviewDao.insertPlaceReview(placeReview)
    }

    suspend fun updatePlaceReview(placeReview: PlaceReview) {
        placeReviewDao.updatePlaceReview(placeReview)
    }

    suspend fun deletePlaceReview(reviewId: Int) {
        placeReviewDao.deletePlaceReview(reviewId)
    }

    suspend fun getPlaceReviewById(reviewId: Int) {
        placeReviewDao.getPlaceReviewById(reviewId)
    }

    suspend fun getAllPlaceReviews() {
        placeReviewDao.getAllPlaceReviews()
    }

    suspend fun getPlaceReviewsByPlaceId(placeId: Int) {
        placeReviewDao.getPlaceReviewsByPlaceId(placeId)
    }

    suspend fun getPlaceReviewsByUserId(userId: Int) {
        placeReviewDao.getPlaceReviewsByUserId(userId)
    }

    suspend fun getPlaceReviewsWithDetailsByPlaceId(placeId: Int): List<PlaceReviewWithDetails> {
        return placeReviewDao.getPlaceReviewsWithDetailsByPlaceId(placeId)
    }

}