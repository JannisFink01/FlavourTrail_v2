package com.example.flovourtrail_v1.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.flovourtrail_v1.database.entity.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Insert
    suspend fun insertAll(vararg users: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM User WHERE user_id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT * FROM User")
    suspend fun getAllUsers(): List<User>
}