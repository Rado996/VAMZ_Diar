package com.Fili.vamz_diar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Fili.vamz_diar.classes.TodoItem
import com.Fili.vamz_diar.classes.TodoList
import com.Fili.vamz_diar.databinding.FragmentTodoDetailBinding
import com.Fili.vamz_diar.groupieItems.TodoGroupieItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


private const val NavigationParameter = "todo"


/**
 * A simple [Fragment] subclass.
 * Use the [TodoDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoDetailFragment : Fragment() {

    private var TodoParam: TodoList? = null
    private var _binding: FragmentTodoDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val adapter = GroupAdapter<GroupieViewHolder>()

    val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            TodoParam = it.getParcelable<TodoList>(NavigationParameter)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTodoDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.todoDetailName.text = TodoParam!!.todoListName
        binding.todoDetailRecyclerview.adapter = adapter
        binding.todoDetailRecyclerview.layoutManager = LinearLayoutManager(context)
        adapter.update(TodoParam!!.todos!!.toTodoGroupieItem())
        setupOnclick()
    }

    private fun setupOnclick() {
        binding.todoEditButton.setOnClickListener { findNavController().navigate(TodoDetailFragmentDirections.actionTodoDetailFragmentToNewTodoFragment(todolist = TodoParam )) }
        binding.todoDeleteButton.setOnClickListener { viewModel.deleteTodoList(TodoParam!!,view) }
    }

    /**
     * method map mutableMap of strings and TodoItems
     * to List of TodoGroupieItem for groupie adapter of recycler view
     */
    private fun MutableMap<String,TodoItem>.toTodoGroupieItem(): List<TodoGroupieItem> {
        return this.map {
            TodoGroupieItem(it.value, TodoParam!!.todoListID.toString())
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TodoDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(Todo: TodoList) =
            TodoDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(NavigationParameter, Todo)
                }
            }
    }
}