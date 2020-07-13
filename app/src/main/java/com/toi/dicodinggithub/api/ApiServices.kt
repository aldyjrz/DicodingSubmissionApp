package com.toi.dicodinggithub.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("search/users")
    fun searchUser(@Query("q") q: String?): Call<SearchResponse>

    @GET("users/{fullUrl}")
    open fun getUsers(
        @Path(
            value = "fullUrl",
            encoded = true
        ) fullUrl: String?
    ): Call<Users>



}