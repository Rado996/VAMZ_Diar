package com.Fili.vamz_diar.groupieItems

import android.view.View

import com.Fili.vamz_diar.R
import com.Fili.vamz_diar.classes.TodoItem
import com.Fili.vamz_diar.databinding.TodolistitemItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.viewbinding.BindableItem

class TodoGroupieItem (private val todoItem: TodoItem, private val todoListID: String) : BindableItem<TodolistitemItemBinding>(){
    /**
     * Class that displays TodoItem data in recycler view in TodoDetailFragment.
     *
     * @param viewBinding The ViewBinding to bind
     * @param position The adapter position
     */

    override fun bind(viewBinding: TodolistitemItemBinding, position: Int) {
        viewBinding.todoitemDetailItem.text = todoItem.itemName
        viewBinding.todoitemDetailCheckbox.isChecked = todoItem.itemState!!
        viewBinding.todoitemDetailCheckbox.setOnCheckedChangeListener { button, state ->
            todoItem.itemState = state
            FirebaseAuth.getInstance().currentUser?.uid.toString()
            FirebaseDatabase.getInstance().getReference("${FirebaseAuth.getInstance().currentUser!!.uid}/Todos/${todoListID}/todos/itemName").setValue(state)
        }
    }

    override fun getLayout(): Int {
        return R.layout.todolistitem_item
    }

    override fun initializeViewBinding(view: View): TodolistitemItemBinding {
        return  TodolistitemItemBinding.bind(view)
    }
}