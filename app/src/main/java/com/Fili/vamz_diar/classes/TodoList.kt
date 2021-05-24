package com.Fili.vamz_diar.classes


import android.os.Parcel
import android.os.Parcelable

/**
 * Class for keeping data about TodoList
 * @param todoListID Id of todoList
 * @param todoListName name of todoList
 * @param todos Mutable map of todoItems
 * @constructor creates instance of todoList
 */

class TodoList( val todoListID: String? = null,
                val todoListName: String? = null,
                val todos: MutableMap<String, TodoItem>? = null
                ):Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(todoListID)
        parcel.writeString(todoListName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TodoList> {
        override fun createFromParcel(parcel: Parcel): TodoList {
            return TodoList(parcel)
        }

        override fun newArray(size: Int): Array<TodoList?> {
            return arrayOfNulls(size)
        }
    }
}