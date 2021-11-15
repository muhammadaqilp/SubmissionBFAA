package com.example.submissionbfaa2.data.api

import com.example.submissionbfaa2.data.response.SearchResponse
import com.example.submissionbfaa2.data.response.UserItem
import com.example.submissionbfaa2.data.response.UserItemDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubUserApi {
    @GET("search/users")
    fun searchUser(
        @Query("q") username: String,
        @Header("Authorization") token: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String, @Header("Authorization") token: String
    ): Call<UserItemDetail>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<List<UserItem>>

    @GET("  users/{username}/following")
    fun getFollowings(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<List<UserItem>>
}