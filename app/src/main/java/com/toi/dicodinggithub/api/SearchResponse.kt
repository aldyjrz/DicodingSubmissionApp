package com.toi.dicodinggithub.api

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class SearchResponse(
    @SerializedName("items")
    val users: ArrayList<Users>
)