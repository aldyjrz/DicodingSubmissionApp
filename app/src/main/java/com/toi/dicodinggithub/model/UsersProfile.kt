package com.toi.dicodinggithub.model

import android.os.Parcel
import android.os.Parcelable

data class UsersProfile(
    val login: String?

): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(login)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UsersProfile> {
        override fun createFromParcel(parcel: Parcel): UsersProfile {
            return UsersProfile(parcel)
        }

        override fun newArray(size: Int): Array<UsersProfile?> {
            return arrayOfNulls(size)
        }
    }
}