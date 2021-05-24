package com.Fili.vamz_diar.classes

import android.os.Parcel
import android.os.Parcelable

/**
 * Class for keeping instance of Note
 * @param noteID ID of Note
 * @param noteName Name od Note
 * @param noteText text of Note
 */

class Note ( val noteID: String? = null, val noteName: String? = null, val noteText: String? = null) :Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(noteID)
        p0?.writeString(noteName)
        p0?.writeString(noteText)
    }


}