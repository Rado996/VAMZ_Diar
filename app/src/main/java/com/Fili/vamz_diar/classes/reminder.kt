package com.Fili.vamz_diar.classes

import android.os.Parcel
import android.os.Parcelable

/**
 * Class representing instance of reminder object
 * @param reminderID Id of reminder
 * @param reminderName name of reminder
 * @param reminderDesc description of reminder
 * @param reminderDate date of reminder
 * @param reminderTime time of reminder
 * @constructor
 */

class reminder(val reminderID: String? = null,
               val reminderName: String? = null,
               val reminderDesc: String? = null,
               val reminderDate: String? = null,
               val reminderTime: String? = null): Parcelable{


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(reminderID)
        parcel.writeString(reminderName)
        parcel.writeString(reminderDesc)
        parcel.writeString(reminderDate)
        parcel.writeString(reminderTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<reminder> {
        override fun createFromParcel(parcel: Parcel): reminder {
            return reminder(parcel)
        }

        override fun newArray(size: Int): Array<reminder?> {
            return arrayOfNulls(size)
        }
    }
}