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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val adapter = GroupAdapter<GroupieViewHolder>()

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NotesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }





}




