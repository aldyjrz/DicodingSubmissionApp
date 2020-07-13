package com.toi.dicodinggithub.api

import com.google.gson.annotations.SerializedName


data class UserDataResponse(
    @SerializedName("items")
    val users: ArrayList<Users>
)