package com.example.submissionbfaa2.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionbfaa2.database.FavoriteUser
import com.example.submissionbfaa2.repository.FavoriteUserRepository

class FavUserInsDelViewModel(application: Application) : ViewModel() {

    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }

    fun checkFavorite(id: String): LiveData<Boolean> =
        mFavoriteUserRepository.checkFavorite(id)

}