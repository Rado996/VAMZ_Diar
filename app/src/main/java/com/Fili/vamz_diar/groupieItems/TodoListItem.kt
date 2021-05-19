package com.Fili.vamz_diar.groupieItems

import android.view.View
import androidx.navigation.findNavController
import com.Fili.vamz_diar.R
import com.Fili.vamz_diar.TodosFragmentDirections
import com.Fili.vamz_diar.classes.TodoList
import com.Fili.vamz_diar.databinding.TodolistItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class TodoListItem(private val todoList: TodoList) : BindableItem<TodolistItemBinding>() {
    /**
     * Perform any actions required to set up the view for display.
     *
     * @param viewBinding The ViewBinding to bind
     * @param position The adapter position
     */
    override fun bind(viewBinding: TodolistItemBinding, position: Int) {
        viewBinding.todoButton.text = todoList.todoListName
        viewBinding.todoButton.setOnClickListener {
            val action = TodosFragmentDirections.actionTodosFragmentToTodoDetailFragment(todo = todoList)
            it.findNavController().navigate(action)
        }
    }

    override fun getLayout(): Int {
        return R.layout.todolist_item
    }

    override fun initializeViewBinding(view: View): TodolistItemBinding {
        return  TodolistItemBinding.bind(view)
    }
}