package com.Fili.vamz_diar.groupieItems

import android.view.View
import com.Fili.vamz_diar.R
import com.Fili.vamz_diar.databinding.TodolistitemItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class TodoItem (private val todoItem: String) : BindableItem<TodolistitemItemBinding>(){
    /**
     * Perform any actions required to set up the view for display.
     *
     * @param viewBinding The ViewBinding to bind
     * @param position The adapter position
     */
    override fun bind(viewBinding: TodolistitemItemBinding, position: Int) {
        viewBinding.todoitemDetailItem.text = todoItem
    }

    override fun getLayout(): Int {
        return R.layout.todolistitem_item
    }

    override fun initializeViewBinding(view: View): TodolistitemItemBinding {
        return  TodolistitemItemBinding.bind(view)
    }
}