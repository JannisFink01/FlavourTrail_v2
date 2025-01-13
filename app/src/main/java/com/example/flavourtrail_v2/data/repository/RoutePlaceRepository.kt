package com.example.flavourtrail_v2.data.repository

import com.example.flavourtrail_v2.data.dao.RoutePlaceDao
import com.example.flavourtrail_v2.data.entity.RoutePlace

class RoutePlaceRepository(private val routePlaceDao: RoutePlaceDao) {
    suspend fun insert(routePlace: RoutePlace) {
        routePlaceDao.insert(routePlace)
    }

    suspend fun insertAll(vararg routePlaces: RoutePlace) {
        routePlaceDao.insertAll(*routePlaces)
    }

    suspend fun update(routePlace: RoutePlace) {
        routePlaceDao.update(routePlace)
    }

    suspend fun delete(routePlace: RoutePlace) {
        routePlaceDao.delete(routePlace)
    }

    suspend fun getRoutePlaceById(routePlaceId: Int): RoutePlace? {
        return routePlaceDao.getRoutePlaceById(routePlaceId)
    }

    suspend fun getAllRoutePlaces(): List<RoutePlace> {
        return routePlaceDao.getAllRoutePlaces()
    }
}