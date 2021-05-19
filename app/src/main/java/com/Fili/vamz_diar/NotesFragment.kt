package com.Fili.vamz_diar

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Fili.vamz_diar.classes.Note
import com.Fili.vamz_diar.databinding.FragmentNotesBinding
import com.Fili.vamz_diar.groupieItems.NoteItem

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
        binding.createNewNotebtn.setOnClickListener { createNewNote() }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        if(viewModel.FirebaseAuthInstance.currentUser == null) {
            val action = NotesFragmentDirections.actionNotesFragmentToLoginFragment()
            // Navigate using that action
            findNavController().navigate(action)
        }
        loadNotes()

    }

    private fun loadNotes() {
//        adapter.addAll(viewModel.notesList.toNoteItem())
        viewModel.notesList.observe(viewLifecycleOwner, Observer {
//            adapter.update(it.toNoteItem())
            adapter.update(it.toNoteItem())
        })
    }

    private fun List<Note>.toNoteItem(): List<NoteItem> {
        return this.map {
            NoteItem(it)
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




