package com.Fili.vamz_diar.groupieItems


import android.view.View
import androidx.navigation.findNavController
import com.Fili.vamz_diar.NotesFragmentDirections
import com.Fili.vamz_diar.R
import com.Fili.vamz_diar.classes.Note
import com.Fili.vamz_diar.databinding.NotesItemBinding

import com.xwray.groupie.viewbinding.BindableItem

class NoteGroupieItem(private val pnote: Note): BindableItem<NotesItemBinding>() {
    /**
     * Perform any actions required to set up the view for display.
     *
     * @param viewBinding The ViewBinding to bind
     * @param position The adapter position
     */
    override fun bind(viewBinding: NotesItemBinding, position: Int) {
        viewBinding.noteButton.text = pnote.noteName
        viewBinding.noteButton.setOnClickListener {
            // Create an action from notesFragment to noteDetailFragment
            // using the required arguments
            val action = NotesFragmentDirections.actionNotesFragmentToNoteDetailFragment(note = pnote)
            // Navigate using that action
            it.findNavController().navigate(action)
        }
    }

    override fun getLayout(): Int {
        return R.layout.notes_item
    }

    override fun initializeViewBinding(view: View): NotesItemBinding {
        return  NotesItemBinding.bind(view)
    }
}



