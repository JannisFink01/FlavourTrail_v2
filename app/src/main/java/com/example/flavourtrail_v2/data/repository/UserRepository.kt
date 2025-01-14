package com.example.flavourtrail_v2.data.repository

import com.example.flavourtrail_v2.data.dao.UserDao
import com.example.flavourtrail_v2.data.entity.User

class UserRepository(private val userDao: UserDao) {


    suspend fun insertUser(user: User) {
        val existingUser = userDao.getUserById(user.userId)
        if (existingUser == null) {
            userDao.insert(user)
        } else {
            userDao.update(user)
        }
    }

    suspend fun insertAllUsers(vararg users: User) {
        for (user in users) {
            val existingUser = userDao.getUserById(user.userId)
            if (existingUser == null) {
                userDao.insert(user)
            } else {
                userDao.update(user)
            }
        }
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.delete(user)
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }
}