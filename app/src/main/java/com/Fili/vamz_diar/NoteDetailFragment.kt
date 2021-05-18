package com.Fili.vamz_diar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.Fili.vamz_diar.classes.Note
import com.Fili.vamz_diar.databinding.FragmentNoteDetailBinding



private const val NavigationParameter = "note"

/**
 * A simple [Fragment] subclass.
 * Use the [NoteDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoteDetailFragment : Fragment() {

    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var note:Note
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = it.getParcelable<Note>(NavigationParameter)!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.noteNameDetail.text = note.noteName
        binding.noteTextDetail.text = note.noteText
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param note Parameter 1.
         * @return A new instance of fragment NoteDetailFragment.
         */

        @JvmStatic
        fun newInstance(note: Note) =
            NoteDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(NavigationParameter,note)
                }
            }
    }
}