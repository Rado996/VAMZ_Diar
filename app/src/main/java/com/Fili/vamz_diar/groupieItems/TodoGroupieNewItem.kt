package com.Fili.vamz_diar.groupieItems

import android.view.View
import com.Fili.vamz_diar.R
import com.Fili.vamz_diar.databinding.TodolistitemItemBinding
import com.Fili.vamz_diar.databinding.TodonewitemItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class TodoGroupieNewItem (private val todoItemName: String) : BindableItem<TodonewitemItemBinding>(){
    /**
     * Perform any actions required to set up the view for display.
     *
     * @param viewBinding The ViewBinding to bind
     * @param position The adapter position
     */


    override fun bind(viewBinding: TodonewitemItemBinding, position: Int) {
        viewBinding.newTodoitemItem.text = todoItemName
    }

    override fun getLayout(): Int {
        return R.layout.todonewitem_item
    }

    override fun initializeViewBinding(view: View): TodonewitemItemBinding {
        return  TodonewitemItemBinding.bind(view)
    }
}