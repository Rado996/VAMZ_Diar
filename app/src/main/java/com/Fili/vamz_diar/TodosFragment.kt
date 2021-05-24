package com.Fili.vamz_diar

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Fili.vamz_diar.classes.TodoList
import com.Fili.vamz_diar.databinding.FragmentTodosBinding
import com.Fili.vamz_diar.groupieItems.TodoGroupieListItem
import com.google.firebase.auth.FirebaseAuth
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
        setHasOptionsMenu(true)
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
        if(viewModel.FirebaseAuthInstance.currentUser == null) {
            val action = TodosFragmentDirections.actionTodosFragmentToLoginFragment()
            // Navigate using that action
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        loadTodos()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logoutBtn -> {  if (viewModel.FirebaseAuthInstance.currentUser != null){
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(context, R.string.logout_message, Toast.LENGTH_SHORT).show()
                val action = TodosFragmentDirections.actionTodosFragmentToLoginFragment()
                findNavController().navigate(action)
            } else
                Toast.makeText(context, R.string.not_logged_in, Toast.LENGTH_SHORT).show()

                return true
            }
            // Otherwise, do nothing and use the core event handling
            // when clauses require that all possible paths be accounted for explicitly,
            // for instance both the true and false cases if the value is a Boolean,
            // or an else to catch all unhandled cases.
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * setup onClick listeners for buttons
     */
    private fun setupOnclick() {

        binding.goToNotesFromTodos.setOnClickListener { findNavController().navigate(TodosFragmentDirections.actionTodosFragmentToNotesFragment()) }
        binding.createNewTodobtn.setOnClickListener { findNavController().navigate(TodosFragmentDirections.actionTodosFragmentToNewTodoFragment(todolist = null)) }
        binding.goToRemindersFromTodos.setOnClickListener { findNavController().navigate(TodosFragmentDirections.actionTodosFragmentToRemindersFragment()) }
    }
    /**
     * method to setup observer for todosList mutableLiveData and updating adapter of recycler view
     */
    private fun loadTodos() {
        viewModel.todosList.observe(viewLifecycleOwner, Observer {
            adapter.update(it.toTodoListItem())
        })
    }
    /**
     * method map List of TodoLists to List of TodoGroupieListitems for groupie adapter of recycler view
     */
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