package com.example.flavourtrail_v2.data.repository

import com.example.flovourtrail_v2.data.dao.RouteDao
import com.example.flovourtrail_v2.data.entity.Route

class RouteRepository(private val routeDao: RouteDao) {
    suspend fun insertRoute(route: Route) {
        routeDao.insert(route)
    }
    suspend fun insertAllRoutes(vararg routes: Route) {
        routeDao.insertAll(*routes)
    }
    suspend fun updateRoute(route: Route) {
        routeDao.update(route)
    }
    suspend fun deleteRoute(route: Route) {
        routeDao.delete(route)
    }
    suspend fun getRouteById(routeId: Int): Route? {
        return routeDao.getRouteById(routeId)
    }
    suspend fun getAllRoutes(): List<Route> {
        return routeDao.getAllRoutes()
    }
}