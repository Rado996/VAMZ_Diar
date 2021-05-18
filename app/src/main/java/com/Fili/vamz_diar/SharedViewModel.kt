package com.Fili.vamz_diar

import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Fili.vamz_diar.classes.Note

class SharedViewModel: ViewModel() {
    private var _notesList = MutableLiveData<List<Note>>()
    val notesList: MutableLiveData<List<Note>>
    get() = _notesList

    private val firebaseNotes= mutableListOf<Note>()

    init {
        loadNotes()

    }


    fun saveNewNote(noteName: String, noteText: String){

        firebaseNotes.add(Note(noteName, noteText))
//        _notesList.apply {
//            value = firebaseNotes.toList()
//        }
    }

    fun loadNotes() {
//        _notesList.apply {
//            value = listOf(Note("Prvá poznamka", "Text prvej poznamky"))
//            value = listOf(Note("Druhá poznamka", "Text druhej poznamky"))
//            value = listOf(Note("Truhá poznamka", "Text druhej poznamky"))
//        }
        firebaseNotes.add(Note("Prvá poznamka", "Text prvej poznamky"))
        firebaseNotes.add(Note("Druhá poznamka", "Text druhej poznamky"))
        firebaseNotes.add(Note("Truhá poznamka", "Text druhej poznamky"))
        _notesList.postValue(firebaseNotes)

//        _notesList.postValue(Note("Prvá poznamka", "Text prvej poznamky"))
//        _notesList.postValue(Note("Druhá poznamka", "Text druhej poznamky"))
    }


}