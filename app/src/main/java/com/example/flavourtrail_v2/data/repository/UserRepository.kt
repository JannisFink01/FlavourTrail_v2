package com.example.flavourtrail_v2.data.repository

import com.example.flovourtrail_v1.database.dao.UserDao
import com.example.flovourtrail_v1.database.entity.User

class UserRepository ( private val userDao: UserDao) {

    suspend fun insertUser(user : User){
        userDao.insert(user)
    }
    suspend fun insertAllUsers(vararg users: User){
        userDao.insertAll(*users)
    }
    suspend fun updateUser(user: User){
        userDao.update(user)
    }
    suspend fun deleteUser(user: User){
        userDao.delete(user)
    }
    suspend fun getUserById(userId: Int): User?{
        return userDao.getUserById(userId)
    }
    suspend fun getAllUsers(): List<User>{
        return userDao.getAllUsers()
    }
}