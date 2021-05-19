package com.Fili.vamz_diar

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Fili.vamz_diar.classes.Note
import com.Fili.vamz_diar.classes.TodoList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class SharedViewModel: ViewModel() {

    private val firebaseTodos= mutableListOf<TodoList>()
    private var _todosList = MutableLiveData<List<TodoList>>()
    val todosList: MutableLiveData<List<TodoList>>
        get() = _todosList

    private val newtodoItemsList = mutableListOf<String>()
    private val _newTodoItemsLiveData = MutableLiveData<List<String>>()
    val newTodoItemsLiveData: MutableLiveData<List<String>>
        get() = _newTodoItemsLiveData

    private var _notesList = MutableLiveData<List<Note>>()
    val notesList: MutableLiveData<List<Note>>
    get() = _notesList

    private val firebaseNotes= mutableListOf<Note>()
    private var _FirebaseAuthInstance = FirebaseAuth.getInstance()
    val FirebaseAuthInstance: FirebaseAuth
    get() = _FirebaseAuthInstance

    private var fireDatabase = FirebaseDatabase.getInstance()

    private var _logedIn = MutableLiveData<Boolean?>()
    val logedIn: MutableLiveData<Boolean?>
        get() = _logedIn

    private var _registered = MutableLiveData<Boolean?>()
    val registered: MutableLiveData<Boolean?>
        get() = _registered

    init {
        loadNotes()
        loadTodos()

    }

    private fun loadTodos() {
        if(FirebaseAuthInstance.currentUser != null) {
            fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Todos").addChildEventListener(
                object : ChildEventListener {
                    /**
                     * This method is triggered when a new child is added to the location to which this listener was
                     * added.
                     *
                     * @param snapshot An immutable snapshot of the data at the new child location
                     * @param previousChildName The key name of sibling location ordered before the new child. This
                     * will be null for the first child node of a location.
                     */
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        val todo = snapshot.getValue(TodoList::class.java)
                        firebaseTodos.add(todo!!)
                        _todosList.postValue(firebaseTodos)
                    }

                    /**
                     * This method is triggered when the data at a child location has changed.
                     *
                     * @param snapshot An immutable snapshot of the data at the new data at the child location
                     * @param previousChildName The key name of sibling location ordered before the child. This will
                     * be null for the first child node of a location.
                     */
                    override fun onChildChanged(snapshot: DataSnapshot,previousChildName: String?) {

                    }

                    /**
                     * This method is triggered when a child is removed from the location to which this listener was
                     * added.
                     *
                     * @param snapshot An immutable snapshot of the data at the child that was removed.
                     */
                    override fun onChildRemoved(snapshot: DataSnapshot) {

                    }

                    /**
                     * This method is triggered when a child location's priority changes. See [ ][DatabaseReference.setPriority] and [Ordered Data](https://firebase.google.com/docs/database/android/retrieve-data#data_order) for more information on priorities and ordering data.
                     *
                     * @param snapshot An immutable snapshot of the data at the location that moved.
                     * @param previousChildName The key name of the sibling location ordered before the child
                     * location. This will be null if this location is ordered first.
                     */
                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                    }

                    /**
                     * This method will be triggered in the event that this listener either failed at the server, or
                     * is removed as a result of the security and Firebase rules. For more information on securing
                     * your data, see: [ Security Quickstart](https://firebase.google.com/docs/database/security/quickstart)
                     *
                     * @param error A description of the error that occurred
                     */
                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }
    }


    fun saveNewNote(noteName: String, noteText: String){

        if(FirebaseAuthInstance.currentUser != null) {
            val newNote = Note(noteName, noteText)
            fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Notes").push().setValue(newNote).addOnSuccessListener {

            }
        }
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
        if(FirebaseAuthInstance.currentUser != null) {
            fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Notes").addChildEventListener(
                object : ChildEventListener {
                    /**
                     * This method is triggered when a new child is added to the location to which this listener was
                     * added.
                     *
                     * @param snapshot An immutable snapshot of the data at the new child location
                     * @param previousChildName The key name of sibling location ordered before the new child. This
                     * will be null for the first child node of a location.
                     */
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        val note = snapshot.getValue(Note::class.java)
                        firebaseNotes.add(note!!)
                        _notesList.postValue(firebaseNotes)
                    }

                    /**
                     * This method is triggered when the data at a child location has changed.
                     *
                     * @param snapshot An immutable snapshot of the data at the new data at the child location
                     * @param previousChildName The key name of sibling location ordered before the child. This will
                     * be null for the first child node of a location.
                     */
                    override fun onChildChanged(snapshot: DataSnapshot,previousChildName: String?) {

                    }

                    /**
                     * This method is triggered when a child is removed from the location to which this listener was
                     * added.
                     *
                     * @param snapshot An immutable snapshot of the data at the child that was removed.
                     */
                    override fun onChildRemoved(snapshot: DataSnapshot) {

                    }

                    /**
                     * This method is triggered when a child location's priority changes. See [ ][DatabaseReference.setPriority] and [Ordered Data](https://firebase.google.com/docs/database/android/retrieve-data#data_order) for more information on priorities and ordering data.
                     *
                     * @param snapshot An immutable snapshot of the data at the location that moved.
                     * @param previousChildName The key name of the sibling location ordered before the child
                     * location. This will be null if this location is ordered first.
                     */
                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                    }

                    /**
                     * This method will be triggered in the event that this listener either failed at the server, or
                     * is removed as a result of the security and Firebase rules. For more information on securing
                     * your data, see: [ Security Quickstart](https://firebase.google.com/docs/database/security/quickstart)
                     *
                     * @param error A description of the error that occurred
                     */
                    override fun onCancelled(error: DatabaseError) {

                    }

                })

        }
//        _notesList.postValue(Note("Prvá poznamka", "Text prvej poznamky"))
//        _notesList.postValue(Note("Druhá poznamka", "Text druhej poznamky"))
    }

    fun LogInUser(userEmail: String, userPassword: String) {
        FirebaseAuthInstance.signInWithEmailAndPassword(userEmail,userPassword).addOnSuccessListener {
            _logedIn.postValue(true)
        }
    }

    fun registerNewUser(userEmail: String, userPassword: String) {
        FirebaseAuthInstance.createUserWithEmailAndPassword(userEmail,userPassword).addOnSuccessListener {
            _registered.postValue(true)
            _logedIn.postValue(true)
        }
            .addOnFailureListener {
                Log.e(TAG, "registerNewUser: ${it}", )
            }
    }

    fun addNewTodoItem(todoItem: String) {
        newtodoItemsList.add(todoItem)
        _newTodoItemsLiveData.postValue(newtodoItemsList)
    }

    fun saveNewTodoList(todoListName: String) {
        val todoItems = mutableMapOf<String,Boolean>()
        newtodoItemsList.forEach { todoItems.put(it,false)  }
        val newTodoList = TodoList(todoListName, todoItems)
        fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Todos").push().setValue(newTodoList).addOnSuccessListener {

        }

    }

}