package com.example.flavourtrail_v2.data.repository

import com.example.flavourtrail_v2.data.dao.RouteReviewDao
import com.example.flavourtrail_v2.data.entity.RouteReview

class RouteReviewRepository(private val routeReviewDao: RouteReviewDao) {
    suspend fun insertRouteReview(routeReview: RouteReview) {
        routeReviewDao.insert(routeReview)
    }

    suspend fun insertAllRouteReviews(vararg routeReviews: RouteReview) {
        routeReviewDao.insertAll(*routeReviews)
    }

    suspend fun updateRouteReview(routeReview: RouteReview) {
        routeReviewDao.update(routeReview)
    }

    suspend fun deleteRouteReview(routeReview: RouteReview) {
        routeReviewDao.delete(routeReview)
    }

    suspend fun getRouteReviewById(routeReviewId: Int) {
        routeReviewDao.getRouteReviewById(routeReviewId)
    }

    suspend fun getAllRouteReviews() {
        routeReviewDao.getAllRouteReviews()
    }
}