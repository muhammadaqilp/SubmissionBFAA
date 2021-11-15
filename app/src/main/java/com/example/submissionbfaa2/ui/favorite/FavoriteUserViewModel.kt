package com.example.submissionbfaa2.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionbfaa2.database.FavoriteUser
import com.example.submissionbfaa2.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {

    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun getAllListFavorite(): LiveData<List<FavoriteUser>> =
        mFavoriteUserRepository.getAllFavoriteUser()
}