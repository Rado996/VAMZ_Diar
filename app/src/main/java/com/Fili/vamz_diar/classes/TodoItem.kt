package com.Fili.vamz_diar.classes

import android.os.Parcel
import android.os.Parcelable

class TodoItem (  val itemName: String? = null, private var _itemState: Boolean? = null ) : Parcelable {

    var itemState: Boolean?
        get() = _itemState
        set(value) {_itemState = value}

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(itemName)
        parcel.writeValue(itemState)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TodoItem> {
        override fun createFromParcel(parcel: Parcel): TodoItem {
            return TodoItem(parcel)
        }

        override fun newArray(size: Int): Array<TodoItem?> {
            return arrayOfNulls(size)
        }
    }
}