package com.Fili.vamz_diar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Fili.vamz_diar.classes.TodoList
import com.Fili.vamz_diar.databinding.FragmentTodosBinding
import com.Fili.vamz_diar.groupieItems.TodoGroupieListItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder



/**
 * A simple [Fragment] subclass.
 * Use the [TodosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodosFragment : Fragment() {

    private var _binding: FragmentTodosBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val viewModel: SharedViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentTodosBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.todosRecyclerView
        setupOnclick()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        loadTodos()
    }

    private fun setupOnclick() {
        binding.createNewTodobtn.setOnClickListener { findNavController().navigate(TodosFragmentDirections.actionTodosFragmentToNewTodoFragment()) }
        binding.goToNotesFromTodos.setOnClickListener { findNavController().navigate(TodosFragmentDirections.actionTodosFragmentToNotesFragment()) }
        binding.createNewTodobtn.setOnClickListener { findNavController().navigate(TodosFragmentDirections.actionTodosFragmentToNewTodoFragment()) }
        binding.goToRemindersFromTodos.setOnClickListener { findNavController().navigate(TodosFragmentDirections.actionTodosFragmentToRemindersFragment()) }
    }

    private fun loadTodos() {
        viewModel.todosList.observe(viewLifecycleOwner, Observer {
//            adapter.update(it.toNoteItem())
            adapter.update(it.toTodoListItem())
        })
    }

    private fun List<TodoList>.toTodoListItem(): List<TodoGroupieListItem> {
        return this.map {
            TodoGroupieListItem(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}