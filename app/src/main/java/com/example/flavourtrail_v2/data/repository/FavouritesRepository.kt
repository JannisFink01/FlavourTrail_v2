package com.example.flavourtrail_v2.data.repository

import com.example.flovourtrail_v1.database.dao.FavouritesDao
import com.example.flovourtrail_v1.database.entity.Favourites

class FavouritesRepository(private val favouritesDao: FavouritesDao) {

    suspend fun getFavourites() = favouritesDao.getAllFavourites()

    suspend fun getFavouriteById(favouriteId: Int) = favouritesDao.getFavouriteById(favouriteId)

    suspend fun insertFavourite(favourite: Favourites) = favouritesDao.insert(favourite)

    suspend fun instertAllFavourites(vararg favourites: Favourites) = favouritesDao.insertAll(*favourites)

    suspend fun deleteFavourite(favourite: Favourites) = favouritesDao.delete(favourite)

}