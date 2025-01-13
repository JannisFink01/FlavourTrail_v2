package com.example.flavourtrail_v2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.flavourtrail_v2.data.dao.FavouritesDao
import com.example.flavourtrail_v2.data.dao.PlaceDao
import com.example.flavourtrail_v2.data.dao.PlaceReviewDao
import com.example.flavourtrail_v2.data.dao.PlaceTagsDao
import com.example.flavourtrail_v2.data.dao.RouteDao
import com.example.flavourtrail_v2.data.dao.RoutePlaceDao
import com.example.flavourtrail_v2.data.dao.RouteReviewDao
import com.example.flavourtrail_v2.data.dao.UserDao
import com.example.flavourtrail_v2.data.entity.Favourites
import com.example.flavourtrail_v2.data.entity.Place
import com.example.flavourtrail_v2.data.entity.PlaceReview
import com.example.flavourtrail_v2.data.entity.PlaceTags
import com.example.flavourtrail_v2.data.entity.Route
import com.example.flavourtrail_v2.data.entity.RoutePlace
import com.example.flavourtrail_v2.data.entity.RouteReview
import com.example.flavourtrail_v2.data.entity.User
import com.example.flavourtrail_v2.helpers.DateUtils.parseDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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
    version = 3,
    exportSchema = false,
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
    abstract fun placeDao(): PlaceDao
    abstract fun placeReviewDao(): PlaceReviewDao
    abstract fun placeTagsDao(): PlaceTagsDao
    abstract fun routeDao(): RouteDao
    abstract fun routePlaceDao(): RoutePlaceDao
    abstract fun routeReviewDao(): RouteReviewDao
    abstract fun userDao(): UserDao

    //    companion object {
//        const val DATABASE_NAME = "app_database"
//
//        @Volatile
//        private var Instance: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase {
//            return Instance ?: synchronized(this) {
//              Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    DATABASE_NAME
//                )
//                  .build()
//                    .also { Instance = it }
//            }
//        }
//    }
//}
    companion object {
        const val DATABASE_NAME = "app_database"

        @Volatile
        private var instance: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .addCallback(
                    object : Callback() {
                        private suspend fun fillDatabase(database: AppDatabase) {
                            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                            database.apply {
                                // Insert Users
                                userDao().insertAll(
                                    User(
                                        1,
                                        "Alice Müller",
                                        "alice.mueller@example.com",
                                        "securePass123",
                                        true,
                                        "userGirl.jpg"
                                    ),
                                    User(
                                        2,
                                        "Tom Schneider",
                                        "tom.schneider@example.com",
                                        "password789",
                                        false,
                                        "guyInBar.jpg"
                                    )
                                )

                                // Insert Places
                                placeDao().insertAll(
                                    Place(
                                        1,
                                        "Sisyphos",
                                        "Club",
                                        "Hauptstraße 15",
                                        "Berlin",
                                        "10317",
                                        52.497587,
                                        13.503745,
                                        "Ein legendärer Open-Air-Club mit einem einzigartigen Festival-Vibe.",
                                        "Sisyphos.jpg"
                                    ),
                                    Place(
                                        2,
                                        "Berghain",
                                        "Club",
                                        "Am Wriezener Bahnhof",
                                        "Berlin",
                                        "10243",
                                        52.511282,
                                        13.439657,
                                        "Der weltweit bekannte Techno-Club mit unvergesslicher Atmosphäre.",
                                        "Berghain.jpg"
                                    ),
                                    Place(
                                        3,
                                        "Prater Garten",
                                        "Biergarten",
                                        "Kastanienallee 7-9",
                                        "Berlin",
                                        "10435",
                                        52.540120,
                                        13.413460,
                                        "Berlins ältester Biergarten mit einer entspannten Atmosphäre.",
                                        "PraterGarten.jpg"
                                    ),
                                    Place(
                                        4,
                                        "Monkey Bar",
                                        "Bar",
                                        "Budapester Straße 40",
                                        "Berlin",
                                        "10787",
                                        52.507580,
                                        13.336702,
                                        "Eine stylische Bar mit atemberaubendem Blick auf den Berliner Zoo.",
                                        "MonkeyBar.jpg"
                                    ),
                                    Place(
                                        5,
                                        "House of Small Wonder",
                                        "Café",
                                        "Johannisstraße 20",
                                        "Berlin",
                                        "10117",
                                        52.524180,
                                        13.386030,
                                        "Ein charmantes Café mit einem Fokus auf gemütliche Atmosphäre und leckeres Essen.",
                                        "houseofsmallwonders.jpg"
                                    ),
                                    Place(
                                        6,
                                        "Klunkerkranich",
                                        "Bar",
                                        "Karl-Marx-Straße 66",
                                        "Berlin",
                                        "12043",
                                        52.477122,
                                        13.444854,
                                        "Ein Rooftop-Bar-Highlight mit Livemusik und einem atemberaubenden Blick über Berlin.",
                                        "klunkerkranich.jpg"

                                    ),
                                    Place(
                                        7,
                                        "Anomalie Art Club",
                                        "Club",
                                        "Storkower Str. 123",
                                        "Berlin",
                                        "10407",
                                        52.534013,
                                        13.444503,
                                        "Ein einzigartiger Club mit künstlerischem Fokus und alternativer Musik.",
                                        "anomalieArt.jpg"
                                    ),
                                    Place(
                                        8,
                                        "Café Luzia",
                                        "Café/Bar",
                                        "Oranienstraße 34",
                                        "Berlin",
                                        "10999",
                                        52.499002,
                                        13.418398,
                                        "Ein beliebter Treffpunkt in Kreuzberg mit einer entspannten Atmosphäre und guten Drinks.",
                                        "CaféLuzia.jpg"
                                    ),
                                    Place(
                                        9,
                                        "Newton Bar",
                                        "Bar",
                                        "Charlottenstraße 57",
                                        "Berlin",
                                        "10117",
                                        52.513366,
                                        13.391569,
                                        "Eine elegante Bar mit klassischen Cocktails und Fotos von Helmut Newton.",
                                        "NewtonBar.jpg"
                                    ),
                                    Place(
                                        10,
                                        "St. Oberholz",
                                        "Café",
                                        "Rosenthaler Straße 72",
                                        "Berlin",
                                        "10119",
                                        52.529011,
                                        13.401138,
                                        "Ein hipper Coworking-Space und Café, ideal für Freelancer.",
                                        "st.Oberholz.jpg"
                                    )
                                )
                                // Insert Routes
                                routeDao().insertAll(
                                    Route(
                                        1,
                                        "Berliner Nachtleben Tour",
                                        "Eine Tour durch die angesagtesten Clubs und Bars Berlins.",
                                        "Walking",
                                        4.9,
                                        180
                                    ),
                                    Route(
                                        2,
                                        "Berlin Café Crawl",
                                        "Erkunde die gemütlichsten und trendigsten Cafés Berlins.",
                                        "Walking",
                                        4.7,
                                        120
                                    )
                                )

                                // Insert Favourites
                                favouritesDao().insertAll(
                                    Favourites(1, 1, 1),
                                    Favourites(2, 2, 2)
                                )

                                // Insert Route_Places
                                routePlaceDao().insertAll(
                                    RoutePlace(1, 1, 1),  // Sisyphos
                                    RoutePlace(2, 1, 2),  // Berghain
                                    RoutePlace(3, 1, 6),  // Klunkerkranich
                                    RoutePlace(4, 1, 4),  // Monkey Bar
                                    RoutePlace(5, 1, 9),  // Newton Bar
                                    RoutePlace(6, 2, 5),  // House of Small Wonder
                                    RoutePlace(7, 2, 3),  // Prater Garten
                                    RoutePlace(8, 2, 8),  // Café Luzia
                                    RoutePlace(9, 2, 10)  // St. Oberholz
                                )

                                // Insert Place_Tags
                                placeTagsDao().insertAll(
                                    PlaceTags(1, "Club", 1),
                                    PlaceTags(2, "Club", 2),
                                    PlaceTags(3, "Biergarten", 3),
                                    PlaceTags(4, "Bar", 4),
                                    PlaceTags(5, "Café", 5),
                                    PlaceTags(6, "Bar", 6),
                                    PlaceTags(7, "Club", 7),
                                    PlaceTags(8, "Café/Bar", 8),
                                    PlaceTags(9, "Bar", 9),
                                    PlaceTags(10, "Café", 10)
                                )
                                // Insert Routes_Reviews
                                routeReviewDao().insertAll(
                                    RouteReview(
                                        1,
                                        1,
                                        1,
                                        5,
                                        "Perfekte Route für alle, die das Berliner Nachtleben erleben wollen!"
                                    ),
                                    RouteReview(
                                        2,
                                        2,
                                        2,
                                        4,
                                        "Sehr entspannte Tour durch die besten Cafés der Stadt."
                                    )
                                )
                                placeReviewDao().insertAll(
                                    PlaceReview(
                                        reviewId = 1,
                                        placeId = 1,
                                        userId = 1,
                                        rating = 5,
                                        comment = "Amazing place with great vibes!",
                                        date = parseDate("10.09.2024")?: Date()
                                    ),
                                    PlaceReview(
                                        reviewId = 2,
                                        placeId = 2,
                                        userId = 2,
                                        rating = 4,
                                        comment = "Great music and atmosphere.",
                                        date = parseDate("11.09.2024")?:Date()
                                    ),
                                    PlaceReview(
                                        reviewId = 3,
                                        placeId = 3,
                                        userId = 1,
                                        rating = 4,
                                        comment = "Nice place to relax and have a beer.",
                                        date = parseDate("12.09.2024")?: Date()
                                    ),
                                    PlaceReview(
                                        reviewId = 4,
                                        placeId = 4,
                                        userId = 2,
                                        rating = 5,
                                        comment = "Stylish bar with a great view.",
                                        date = parseDate("13.09.2024")?:Date()
                                    ),
                                    PlaceReview(
                                        reviewId = 5,
                                        placeId = 5,
                                        userId = 1,
                                        rating = 5,
                                        comment = "Charming café with delicious food.",
                                        date = parseDate("14.09.2024")?:Date()
                                    ),
                                    PlaceReview(
                                        reviewId = 6,
                                        placeId = 6,
                                        userId = 2,
                                        rating = 5,
                                        comment = "Great rooftop bar with live music.",
                                        date = parseDate("15.09.2024")?:Date()
                                    ),
                                    PlaceReview(
                                        reviewId = 7,
                                        placeId = 7,
                                        userId = 1,
                                        rating = 4,
                                        comment = "Unique club with artistic focus.",
                                        date = parseDate("16.09.2024")?:Date()
                                    ),
                                    PlaceReview(
                                        reviewId = 8,
                                        placeId = 8,
                                        userId = 2,
                                        rating = 4,
                                        comment = "Popular spot in Kreuzberg with good drinks.",
                                        date = parseDate("17.09.2024")?:Date()
                                    ),
                                    PlaceReview(
                                        reviewId = 9,
                                        placeId = 9,
                                        userId = 1,
                                        rating = 5,
                                        comment = "Elegant bar with classic cocktails.",
                                        date = parseDate("18.09.2024")?:Date()
                                    ),
                                    PlaceReview(
                                        reviewId = 10,
                                        placeId = 10,
                                        userId = 2,
                                        rating = 5,
                                        comment = "Hip coworking space and café, ideal for freelancers.",
                                        date = parseDate("2021-07-01")?:Date()
                                    )
                                )
                            }
                        }

                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                fillDatabase(getInstance(context))
                            }
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                fillDatabase(getInstance(context))
                            }
                        }
                    },
                ).build()
    }
}

//    private fun deleteDatabase(context: Context) {
//        val dbFile = context.getDatabasePath(DATABASE_NAME)
//        if (dbFile.exists()) {
//            dbFile.delete()
//        }
//    }
//
