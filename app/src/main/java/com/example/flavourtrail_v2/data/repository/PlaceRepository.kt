package com.example.flavourtrail_v2.data.repository


import com.example.flovourtrail_v2.data.dao.PlaceDao
import com.example.flovourtrail_v2.data.entity.Place

class PlaceRepository(private val placeDao: PlaceDao) {

        suspend fun insertPlace(place: Place) {
            placeDao.insert(place)
        }

        suspend fun insertAllPlaces(vararg places: Place) {
            placeDao.insertAll(*places)
        }

        suspend fun updatePlace(place: Place) {
            placeDao.update(place)
        }

        suspend fun deletePlace(place: Place) {
            placeDao.delete(place)
        }

        suspend fun getPlaceById(placeId: Int): Place? {
            return placeDao.getPlaceById(placeId)
        }

        suspend fun getAllPlaces(): List<Place> {
            return placeDao.getAllPlaces()
        }
}