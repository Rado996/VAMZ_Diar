package com.Fili.vamz_diar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Fili.vamz_diar.classes.TodoList
import com.Fili.vamz_diar.databinding.FragmentNewTodoBinding
import com.Fili.vamz_diar.groupieItems.TodoGroupieItem
import com.Fili.vamz_diar.groupieItems.TodoGroupieNewItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


private const val ARG_PARAM1 = "todo"


/**
 * A simple [Fragment] subclass.
 * Use the [NewTodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewTodoFragment : Fragment() {

    private var param1: TodoList? = null
    private var TodoParam: TodoList? = null
    private var _binding: FragmentNewTodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val viewModel: SharedViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewTodoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.newTodoRecyclerview
        setupOnclicks()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        setupTodosObserver()
    }
    /**
     * Method to set up onClickListeners for buttons
     */
    private fun setupOnclicks() {
        binding.newTodoitemAddbtn.setOnClickListener { addnewTodoItem() }
        binding.newTodoSavebtn.setOnClickListener { savenewTodoList() }
    }

    /**
     * Method to save new TodoList with viewModel
     */
    private fun savenewTodoList() {
        if(adapter.groupCount >0 && !binding.newTodoName.text.isBlank()) {
            viewModel.saveNewTodoList(view, binding.newTodoName.text.toString())
        }
    }

    /**
     * Method to add name of new todoItem into
     * newTodoItemsLiveData in viewModel.
     */
    private fun addnewTodoItem() {
        if(!binding.newTodoitemName.text.isBlank()) {
            if(viewModel.addNewTodoItem(binding.newTodoitemName.text.toString()))
                binding.newTodoitemName.text.clear()
            else
                Toast.makeText(context, R.string.new_todoitem_already_added, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Method to setup observer for liveData attribute of added names of todos
     * and updating adapter of recyclerView.
     */
    private fun setupTodosObserver() {
        viewModel.newTodoItemsLiveData.observe(viewLifecycleOwner, Observer {
            adapter.update(it.toTodoItem())
        })
    }
    /**
     * Method to map List of strings
     * to List of TodoGroupieNewItem for groupie adapter of recycler view
     * to display added names of todos.
     */
    private fun List<String>.toTodoItem(): List<TodoGroupieNewItem> {
        return this.map {
            TodoGroupieNewItem(it)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment NewTodoFragment.
         */
        @JvmStatic
        fun newInstance(param1: TodoList) =
            NewTodoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)

                }
            }
    }
}