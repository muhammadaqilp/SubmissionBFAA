package com.example.submissionbfaa2.data.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total_count")
    val total_count: Int,
    @SerializedName("incomplete_results")
    val incomplete_results: Boolean,
    @SerializedName("items")
    val list_user: List<UserItem>
)
