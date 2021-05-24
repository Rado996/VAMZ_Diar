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
import com.Fili.vamz_diar.classes.Note
import com.Fili.vamz_diar.databinding.FragmentNotesBinding
import com.Fili.vamz_diar.groupieItems.NoteGroupieItem
import com.google.firebase.auth.FirebaseAuth

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
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logoutBtn -> {  if (viewModel.FirebaseAuthInstance.currentUser != null){
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(context, R.string.logout_message, Toast.LENGTH_SHORT).show()
                val action = NotesFragmentDirections.actionNotesFragmentToLoginFragment()
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
     * Setup onClick listeners for views
     */
    private fun setupOnClicks() {
        binding.createNewNotebtn.setOnClickListener { createNewNote() }
        binding.goToTodosFromNotes.setOnClickListener { findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToTodosFragment()) }
        binding.goToRemindersFromNotes.setOnClickListener { findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToRemindersFragment()) }
    }

    /**
     * Setup observer for live data of Notes and store it in adapter for recyclerView
     */
    private fun loadNotes() {
        viewModel.notesList.observe(viewLifecycleOwner, Observer {
            adapter.update(it.toNoteItem())
        })
    }
    /**
     *Converts List of Notes to List of NoteGroupieItems for groupie adapter for recycler view
     * @param List<Note>
     * @return List<NoteGroupieItem>
     */
    private fun List<Note>.toNoteItem(): List<NoteGroupieItem> {
        return this.map {
            NoteGroupieItem(it)
        }
    }
    /**
     * creates action to navigate from notesFragment to createNewNoteFragment
     */
    private fun createNewNote() {
        val action = NotesFragmentDirections.actionNotesFragmentToNewNoteFragment(note = null)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}




