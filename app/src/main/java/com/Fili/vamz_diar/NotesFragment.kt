package com.Fili.vamz_diar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Fili.vamz_diar.classes.Note
import com.Fili.vamz_diar.databinding.FragmentNotesBinding
import com.Fili.vamz_diar.groupieItems.NoteGroupieItem

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder





/**
 * A simple [Fragment] subclass.
 * Use the [NotesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotesFragment : Fragment() {


    private var _binding: FragmentNotesBinding? = null
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
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.notesRecyclerView
        setupOnClicks()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        if(viewModel.FirebaseAuthInstance.currentUser == null) {
            val action = NotesFragmentDirections.actionNotesFragmentToLoginFragment()
            // Navigate using that action
            findNavController().navigate(action)
        }
        loadNotes()

    }

    private fun setupOnClicks() {
        binding.createNewNotebtn.setOnClickListener { createNewNote() }
        binding.goToTodosFromNotes.setOnClickListener { findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToTodosFragment()) }
        binding.goToRemindersFromNotes.setOnClickListener { findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToRemindersFragment()) }
    }

    private fun loadNotes() {
//        adapter.addAll(viewModel.notesList.toNoteItem())
        viewModel.notesList.observe(viewLifecycleOwner, Observer {
//            adapter.update(it.toNoteItem())
            adapter.update(it.toNoteItem())
        })
    }

    private fun List<Note>.toNoteItem(): List<NoteGroupieItem> {
        return this.map {
            NoteGroupieItem(it)
        }
    }

    private fun createNewNote() {
        // Create an action from notesFragment to createNewNoteFragment
        // using the required arguments
        val action = NotesFragmentDirections.actionNotesFragmentToNewNoteFragment(note = null)
        // Navigate using that action
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}




