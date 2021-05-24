package com.Fili.vamz_diar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.Fili.vamz_diar.classes.Note
import com.Fili.vamz_diar.databinding.FragmentNewNoteBinding


private const val NavigationParameter = "note"

/**
 * A simple [Fragment] subclass to create new Note.
 * Use the [NewNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewNoteFragment : Fragment() {

    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!
    private var note: Note? = null

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = it.getParcelable<Note>(NavigationParameter)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.newNoteName.setText(note?.noteName)
        binding.newNoteText.setText(note?.noteText)
        binding.newNoteSavebtn.setOnClickListener { saveNewNote() }

    }

    /**
     * method that uses viewModel method saveNewNote to save new Note to the database
     */
    private fun saveNewNote() {
        viewModel.saveNewNote(_binding?.newNoteName?.text.toString(), _binding?.newNoteText?.text.toString())
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param note Parameter 1.
         *
         * @return A new instance of fragment NewNoteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(note: Note) =
                NewNoteFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(NavigationParameter,note)
                    }
                }
    }
}