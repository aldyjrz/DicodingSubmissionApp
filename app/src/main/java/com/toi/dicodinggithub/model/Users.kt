package com.toi.dicodinggithub.model

import com.google.gson.annotations.SerializedName
data class Users(
    @SerializedName("login")
    var login: String? = null,

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("avatar_url")
    var avatar_url: String? = null,

    @SerializedName("company")
    var company: String? = null,
    @SerializedName("url")
    var url: String? = null,

    @SerializedName("followers_url")
    var followers_url: String? = null,

    @SerializedName("following_url")
    var following_url: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("blog")
    var blog: String? = null,

    @SerializedName("location")
    var location: String? = null,

    @SerializedName("bio")
    var bio: String? = null,

    @SerializedName("public_repos")
    var public_repos: Int? = null,


    @SerializedName("followers")
    var followers: Int? = null,

    @SerializedName("following")
    var following: Int? = null

)