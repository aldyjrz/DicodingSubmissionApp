package com.toi.dicodinggithub.model

import android.os.Parcel
import android.os.Parcelable

data class UserUrl(
    val url: String?

): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserUrl> {
        override fun createFromParcel(parcel: Parcel): UserUrl {
            return UserUrl(parcel)
        }

        override fun newArray(size: Int): Array<UserUrl?> {
            return arrayOfNulls(size)
        }
    }
}