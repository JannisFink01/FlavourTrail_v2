package com.example.flavourtrail_v2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flovourtrail_v1.database.dao.*
import com.example.flovourtrail_v1.database.entity.*

/**
 * Database class with a singleton Instance object.
 */
@Database(
    entities = [
        Favourites::class,
        Place::class,
        PlaceReview::class,
        PlaceTags::class,
        Route::class,
        RoutePlace::class,
        RouteReview::class,
        User::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
    abstract fun placeDao(): PlaceDao
    abstract fun placeReviewDao(): PlaceReviewDao
    abstract fun placeTagsDao(): PlaceTagsDao
    abstract fun routeDao(): RouteDao
    abstract fun routePlaceDao(): RoutePlaceDao
    abstract fun routeReviewDao(): RouteReviewDao
    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "app_database"

        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                    Instance = instance
                instance
            }
        }
    }


}