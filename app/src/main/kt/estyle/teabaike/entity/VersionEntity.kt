package estyle.teabaike.entity

import android.os.Parcel
import android.os.Parcelable


data class VersionEntity(
    val apk_link: String,
    val force_update: Boolean,
    val info: String?,
    val version_code: Long,
    val version_name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(apk_link)
        parcel.writeByte(if (force_update) 1 else 0)
        parcel.writeString(info)
        parcel.writeLong(version_code)
        parcel.writeString(version_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VersionEntity> {
        override fun createFromParcel(parcel: Parcel): VersionEntity {
            return VersionEntity(parcel)
        }

        override fun newArray(size: Int): Array<VersionEntity?> {
            return arrayOfNulls(size)
        }
    }
}