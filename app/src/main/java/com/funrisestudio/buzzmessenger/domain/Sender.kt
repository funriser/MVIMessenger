package com.funrisestudio.buzzmessenger.domain

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes

data class Sender(
    val id: Int,
    val name: String,
    @DrawableRes val avatar: Int
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Sender> {
        override fun createFromParcel(parcel: Parcel): Sender {
            return Sender(parcel)
        }

        override fun newArray(size: Int): Array<Sender?> {
            return arrayOfNulls(size)
        }
    }

}