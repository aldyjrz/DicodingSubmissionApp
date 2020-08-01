package com.toi.dicodinggithub.data

import android.os.Parcel
import android.os.Parcelable
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
    var url: String? = "",

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

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(login)
        parcel.writeString(id)
        parcel.writeString(avatar_url)
        parcel.writeString(company)
        parcel.writeString(url)
        parcel.writeString(followers_url)
        parcel.writeString(following_url)
        parcel.writeString(name)
        parcel.writeString(blog)
        parcel.writeString(location)
        parcel.writeString(bio)
        parcel.writeValue(public_repos)
        parcel.writeValue(followers)
        parcel.writeValue(following)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Users> {
        override fun createFromParcel(parcel: Parcel): Users {
            return Users(parcel)
        }

        override fun newArray(size: Int): Array<Users?> {
            return arrayOfNulls(size)
        }
    }
}