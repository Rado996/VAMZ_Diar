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
import com.Fili.vamz_diar.classes.reminder
import com.Fili.vamz_diar.databinding.FragmentRemindersBinding
import com.Fili.vamz_diar.groupieItems.ReminderGroupieItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


/**
 * A simple [Fragment] subclass that holds all the reminders from database.
 * Use the [RemindersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RemindersFragment : Fragment() {

    private var _binding: FragmentRemindersBinding? = null
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
        _binding = FragmentRemindersBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.remindersRecyclerView
        setupOnClicks()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        if(viewModel.FirebaseAuthInstance.currentUser == null) {
            val action = NotesFragmentDirections.actionNotesFragmentToLoginFragment() // TODO: 22.05.2021
            // Navigate using that action
            findNavController().navigate(action)
        }
        loadReminders()

    }

    /**
     * method to setup observer for remindersList mutableLiveData and updating adapter of recycler view
     */
    private fun loadReminders() {
        viewModel.remindersList.observe(viewLifecycleOwner, Observer {
            adapter.update(it.toReminderItem())
        })
    }

    /**
     * method map List of reminders to List of ReminderGroupieItems for groupie adapter of recycler view
     */
    private fun List<reminder>.toReminderItem(): List<ReminderGroupieItem> {
        return this.map {
            ReminderGroupieItem(it)
        }
    }

    /**
     * setup onClick listeners for buttons
     */
    private fun setupOnClicks() {
        binding.goToNotesFromReminders.setOnClickListener { findNavController().navigate(RemindersFragmentDirections.actionRemindersFragmentToNotesFragment()) }
        binding.goToTodosFromReminders.setOnClickListener { findNavController().navigate(RemindersFragmentDirections.actionRemindersFragmentToTodosFragment()) }
        binding.newReminderbtn.setOnClickListener { findNavController().navigate(RemindersFragmentDirections.actionRemindersFragmentToNewReminderFragment()) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         *
         * @return A new instance of fragment RemindersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            RemindersFragment()
    }
}