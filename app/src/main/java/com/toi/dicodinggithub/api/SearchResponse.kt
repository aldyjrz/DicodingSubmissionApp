package com.toi.dicodinggithub.api

import com.google.gson.annotations.SerializedName
import com.toi.dicodinggithub.model.Users

data class SearchResponse(
    @SerializedName("items")
    val users: ArrayList<Users>
)