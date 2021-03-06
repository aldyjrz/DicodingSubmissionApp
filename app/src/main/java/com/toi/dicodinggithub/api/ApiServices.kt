package com.toi.dicodinggithub.api

import com.toi.dicodinggithub.data.Users
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


    @GET("users/{username}/followers")
    open fun getFollowers(
        @Path(
            value = "username",
            encoded = true
        ) username: String?
    ): Call<Users>

    @GET("users/{username}/following")
    open fun getFollowing(
        @Path(
            value = "username",
            encoded = true
        ) username: String?
    ): Call<Users>
}