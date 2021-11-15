package com.example.submissionbfaa2.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submissionbfaa2.database.FavoriteUser
import com.example.submissionbfaa2.database.UserDao
import com.example.submissionbfaa2.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mUserDao.getAllFavorite()

    fun checkFavorite(id: String): LiveData<Boolean> = mUserDao.isFavorite(id)

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mUserDao.delete(favoriteUser) }
    }
}