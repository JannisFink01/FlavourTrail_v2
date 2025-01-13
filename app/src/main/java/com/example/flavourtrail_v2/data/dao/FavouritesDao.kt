package com.example.flavourtrail_v2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.flavourtrail_v2.data.entity.Favourites

@Dao
interface FavouritesDao {

    @Insert
    suspend fun insert(favourites: Favourites)

    @Insert
    suspend fun insertAll(vararg favourites: Favourites)

    @Update
    suspend fun update(favourites: Favourites)

    @Delete
    suspend fun delete(favourites: Favourites)

    @Query("SELECT * FROM favourites WHERE favourite_id = :favouriteId")
    suspend fun getFavouriteById(favouriteId: Int): Favourites?

    @Query("SELECT * FROM favourites")
    suspend fun getAllFavourites(): List<Favourites>
}