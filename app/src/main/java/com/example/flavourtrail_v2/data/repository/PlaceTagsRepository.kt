package com.example.flavourtrail_v2.data.repository

import com.example.flovourtrail_v1.database.dao.PlaceTagsDao
import com.example.flovourtrail_v1.database.entity.PlaceTags

class PlaceTagsRepository(private val placeTagsDao: PlaceTagsDao) {

        suspend fun insert(placeTags: PlaceTags) {
            placeTagsDao.insert(placeTags)
        }

        suspend fun insertAll(vararg placeTags: PlaceTags) {
            placeTagsDao.insertAll(*placeTags)
        }

        suspend fun update(placeTags: PlaceTags) {
            placeTagsDao.update(placeTags)
        }

        suspend fun delete(placeTags: PlaceTags) {
            placeTagsDao.delete(placeTags)
        }

        suspend fun getPlaceTagsById(placeTagsId : Int): PlaceTags? {
            return placeTagsDao.getPlaceTagById(placeTagsId)
        }
    suspend fun getAllPlaceTags(): List<PlaceTags> {
        return placeTagsDao.getAllPlaceTags()
    }
}