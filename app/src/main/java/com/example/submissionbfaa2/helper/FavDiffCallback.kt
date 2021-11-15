package com.example.submissionbfaa2.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.submissionbfaa2.database.FavoriteUser

class FavDiffCallback(
    private val mOldFavList: List<FavoriteUser>,
    private val mNewFavList: List<FavoriteUser>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavList[oldItemPosition].userId == mNewFavList[newItemPosition].userId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldFavList[oldItemPosition]
        val newEmployee = mNewFavList[newItemPosition]
        return oldEmployee.userId == newEmployee.userId && oldEmployee.username == newEmployee.username
    }

}