package com.example.flavourtrail_v2.data.repository


import com.example.flavourtrail_v2.data.dao.FavouritesDao
import com.example.flavourtrail_v2.data.entity.Favourites

class FavouritesRepository(private val favouritesDao: FavouritesDao) {

    suspend fun getFavourites() = favouritesDao.getAllFavourites()

    suspend fun getFavouriteById(favouriteId: Int) = favouritesDao.getFavouriteById(favouriteId)

    suspend fun insertFavourite(favourite: Favourites) = favouritesDao.insert(favourite)

    suspend fun instertAllFavourites(vararg favourites: Favourites) =
        favouritesDao.insertAll(*favourites)

    suspend fun deleteFavourite(favourite: Favourites) = favouritesDao.delete(favourite)

}