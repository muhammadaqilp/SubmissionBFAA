package com.example.submissionbfaa2.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionbfaa2.BuildConfig
import com.example.submissionbfaa2.data.api.GithubUserApi
import com.example.submissionbfaa2.data.api.ServiceBuilder
import com.example.submissionbfaa2.data.response.SearchResponse
import com.example.submissionbfaa2.data.response.UserItem
import com.example.submissionbfaa2.data.response.UserItemDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val listSearch = MutableLiveData<List<UserItem>>()
    private val userDetail = MutableLiveData<UserItemDetail>()
    private val following = MutableLiveData<List<UserItem>>()
    private val followers = MutableLiveData<List<UserItem>>()

    private var token = BuildConfig.GITHUB_TOKEN
    private val request = ServiceBuilder.buildService(GithubUserApi::class.java)

    fun searchUser(username: String) {
        val call = request.searchUser(username, token)

        call.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listSearch.postValue(it.list_user)
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }

    fun detailUser(username: String) {
        val call = request.getDetail(username, token)

        call.enqueue(object : Callback<UserItemDetail> {
            override fun onResponse(
                call: Call<UserItemDetail>,
                response: Response<UserItemDetail>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        userDetail.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<UserItemDetail>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }

    fun listFollowers(username: String) {
        val call = request.getFollowers(username, token)

        call.enqueue(object : Callback<List<UserItem>> {
            override fun onResponse(
                call: Call<List<UserItem>>,
                response: Response<List<UserItem>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        followers.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }

    fun listFollowing(username: String) {
        val call = request.getFollowings(username, token)

        call.enqueue(object : Callback<List<UserItem>> {
            override fun onResponse(
                call: Call<List<UserItem>>,
                response: Response<List<UserItem>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        following.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }

    fun getSearchUser(): LiveData<List<UserItem>> {
        return listSearch
    }

    fun getDetailUser(): LiveData<UserItemDetail> {
        return userDetail
    }

    fun getFollowers(): LiveData<List<UserItem>> {
        return followers
    }

    fun getFollowing(): LiveData<List<UserItem>> {
        return following
    }
}