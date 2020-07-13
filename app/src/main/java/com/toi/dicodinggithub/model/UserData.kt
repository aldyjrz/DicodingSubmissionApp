package com.toi.dicodinggithub.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserData(
    var login: String? = "",
    var id: Int = 0,
    var avatar_url: String? ="",
    var url: String? = "",
    var followers_url: String? = "",
    var following_url: String? = "",
    var name: String? ="",
    var blog: String? = "",
    var location: String? = "",
    var bio: String? = "",
    var public_repos: Int = 0,
    var followers: Int = 0,
    var following: Int = 0) : Parcelable