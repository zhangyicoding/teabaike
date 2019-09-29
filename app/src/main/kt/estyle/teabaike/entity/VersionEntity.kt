package estyle.teabaike.entity

import android.os.Parcel
import android.os.Parcelable
import estyle.base.entity.NetEntity

data class VersionEntity(val data: DataEntity) : NetEntity() {

    data class DataEntity(
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
        )

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

        companion object CREATOR : Parcelable.Creator<DataEntity> {
            override fun createFromParcel(parcel: Parcel): DataEntity {
                return DataEntity(parcel)
            }

            override fun newArray(size: Int): Array<DataEntity?> {
                return arrayOfNulls(size)
            }
        }
    }
}