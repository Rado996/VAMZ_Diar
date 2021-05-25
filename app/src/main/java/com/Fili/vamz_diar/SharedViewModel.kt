package com.Fili.vamz_diar

import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.Fili.vamz_diar.classes.Note
import com.Fili.vamz_diar.classes.reminder
import com.Fili.vamz_diar.classes.TodoItem
import com.Fili.vamz_diar.classes.TodoList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

/**
 * ViewModel for keeping al the data of aplications as Notes, Todos,
 * reminders as well as comunicating with database
 */

class SharedViewModel: ViewModel() {

    private val firebaseReminders= mutableListOf<reminder>()
    private var _remindersList = MutableLiveData<List<reminder>>()
    val remindersList: MutableLiveData<List<reminder>>
        get() = _remindersList

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
        loadReminders()
    }

    /**
     * Method to setup ChildListeners on database reference where Todos are saved
     *
     */
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
                        val todoListName = snapshot.child("todoListName").value.toString()
                        val todoListID = snapshot.child("todoListID").value.toString()
                        val todoItems = mutableMapOf<String, TodoItem>()
                        snapshot.child("todos").children.forEach {
                            todoItems.put(it.key.toString(), TodoItem(it.key.toString(), it.child("itemState").value.toBoolean()))
                        }
                        val todo = TodoList(todoListID, todoListName, todoItems)
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
                        val todoListName = snapshot.child("todoListName").value.toString()
                        val todoListID = snapshot.child("todoListID").value.toString()
                        val todoItems = mutableMapOf<String, TodoItem>()
                        snapshot.child("todos").children.forEach {
                            todoItems.put(it.key.toString(), TodoItem(it.key.toString(), it.child("itemState").value.toBoolean()))
                        }
                        val todo = TodoList(todoListID,todoListName,  todoItems)
                        firebaseTodos.forEach {
                            if (it.todoListID == todoListID )
                                firebaseTodos.remove(it)
                        }
                        firebaseTodos.add(todo!!)
                        _todosList.postValue(firebaseTodos)
                    }

                    /**
                     * This method is triggered when a child is removed from the location to which this listener was
                     * added.
                     *
                     * @param snapshot An immutable snapshot of the data at the child that was removed.
                     */
                    override fun onChildRemoved(snapshot: DataSnapshot) {
                        val todoListID = snapshot.child("todoListID").value.toString()
                        firebaseTodos.forEach {
                            if (it.todoListID == todoListID )
                                firebaseTodos.remove(it)
                        }
                        _todosList.postValue(firebaseTodos)
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
    /**
     * Method to create new instance of Note, and stores it in database
     * @param noteName name of the Note
     * @param noteText text of the Note
     * @param view to display message and navigate back from form
     */
    fun saveNewNote(note: Note? , noteName: String, noteText: String, view: View?){
        if(FirebaseAuthInstance.currentUser != null) {
            if(note != null){
                val newNote = Note(note.noteID, noteName, noteText)
                fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Notes/${note.noteID}").setValue(newNote).addOnSuccessListener {
                    Toast.makeText(view!!.context, R.string.note_edited, Toast.LENGTH_SHORT).show()
                    view.findNavController().navigateUp()
                }
                    .addOnFailureListener {
                        Toast.makeText(view!!.context, it.toString(), Toast.LENGTH_SHORT).show()
                    }
            }else {
                val ID =
                    fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Notes")
                        .push().key.toString()
                val newNote = Note(ID, noteName, noteText)
                fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Notes/${ID}")
                    .setValue(newNote).addOnSuccessListener {
                    Toast.makeText(view!!.context, R.string.note_added, Toast.LENGTH_SHORT).show()
                    view.findNavController().navigateUp()
                }
                    .addOnFailureListener {
                        Toast.makeText(view!!.context, it.toString(), Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
    /**
     * Method to setup ChildListeners on database reference where Notes are saved     *
     */
    fun loadNotes() {

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
                        val note = snapshot.getValue(Note::class.java)
                        firebaseNotes.forEach {
                            if (it.noteID == note!!.noteID)
                                firebaseNotes.remove(it)
                        }
                        firebaseNotes.add(note!!)
                        _notesList.postValue(firebaseNotes)
                    }

                    /**
                     * This method is triggered when a child is removed from the location to which this listener was
                     * added.
                     *
                     * @param snapshot An immutable snapshot of the data at the child that was removed.
                     */
                    override fun onChildRemoved(snapshot: DataSnapshot) {
                        val note = snapshot.getValue(Note::class.java)
                        firebaseNotes.forEach {
                            if (it.noteID == note!!.noteID)
                                firebaseNotes.remove(it)
                        }
                        _notesList.postValue(firebaseNotes)
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
    /**
     * Method to setup ChildListeners on database reference where Reminders are stored
     */
    fun loadReminders() {
        if(FirebaseAuthInstance.currentUser != null) {
            fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Reminders").addChildEventListener(
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
                        val reminder = snapshot.getValue(reminder::class.java)
                        firebaseReminders.add(reminder!!)
                        _remindersList.postValue(firebaseReminders)
                    }

                    /**
                     * This method is triggered when the data at a child location has changed.
                     *
                     * @param snapshot An immutable snapshot of the data at the new data at the child location
                     * @param previousChildName The key name of sibling location ordered before the child. This will
                     * be null for the first child node of a location.
                     */
                    override fun onChildChanged(snapshot: DataSnapshot,previousChildName: String?) {
                        val reminder = snapshot.getValue(reminder::class.java)
                        firebaseReminders.forEach {
                            if (it.reminderID == reminder!!.reminderID)
                                firebaseReminders.remove(it)
                        }
                        firebaseReminders.add(reminder!!)
                        _remindersList.postValue(firebaseReminders)
                    }

                    /**
                     * This method is triggered when a child is removed from the location to which this listener was
                     * added.
                     *
                     * @param snapshot An immutable snapshot of the data at the child that was removed.
                     */
                    override fun onChildRemoved(snapshot: DataSnapshot) {
                        val reminder = snapshot.getValue(reminder::class.java)
                        firebaseReminders.forEach {
                            if (it.reminderID == reminder!!.reminderID)
                                firebaseReminders.remove(it)
                        }
                        _remindersList.postValue(firebaseReminders)
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

    /**
     * Method to log in user with FirebaseAuth instance using his email and password
     * @param userEmail email of user
     * @param userPassword password of suer
     * @param view to display message and navigate back
     */
    fun LogInUser(userEmail: String, userPassword: String, view: View?) {
        FirebaseAuthInstance.signInWithEmailAndPassword(userEmail,userPassword).addOnSuccessListener {
//            _logedIn.postValue(true)
            Toast.makeText(view?.context, R.string.login_succes, Toast.LENGTH_SHORT).show()
            view!!.findNavController().navigateUp()
        }.addOnFailureListener {
            Toast.makeText(view?.context, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Method to register user with FirebaseAuth instance using his email and password
     * @param userEmail email of user
     * @param userPassword password of suer
     * @param view to display message and navigate back
     */
    fun registerNewUser(userEmail: String, userPassword: String, view: View?) {
        FirebaseAuthInstance.createUserWithEmailAndPassword(userEmail,userPassword).addOnSuccessListener {
//            _registered.postValue(true)
//            _logedIn.postValue(true)
            Toast.makeText(view?.context, R.string.register_succes, Toast.LENGTH_SHORT).show()
            view!!.findNavController().navigateUp()
        }.addOnFailureListener {
            Toast.makeText(view?.context, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Method to add name of new TodoItem into newtodoItemsList
     * @param todoItem name of new TodoItem
     */
    fun addNewTodoItem(todoItem: String): Boolean {
        if(!newtodoItemsList.contains(todoItem)) {
            newtodoItemsList.add(todoItem)
            _newTodoItemsLiveData.postValue(newtodoItemsList)
            return true
        }
        return false
    }
    /**
     * Method to create new instance of TodoList and stores it in database
     * as Todos uses newtodoItemsList and for each element creates new Todoitem
     * @param todoListName name of the Note
     * @param view to display message and navigate back from form
     */
    fun saveNewTodoList(view: View?, todoListName: String, todoList: TodoList?) {
        val todoItems = mutableMapOf<String,TodoItem>()
        newtodoItemsList.forEach { todoItems.put(it, TodoItem(it,false))  }
        if(todoList != null) {
            val newTodoList = TodoList(todoList.todoListID,todoListName, todoItems)
            fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Todos/${todoList.todoListID}").setValue(newTodoList).addOnSuccessListener{
                newtodoItemsList.clear()
                todoItems.clear()
                _newTodoItemsLiveData.postValue(newtodoItemsList)
                Toast.makeText(view!!.context, R.string.todo_edited, Toast.LENGTH_SHORT).show()
                view.findNavController().navigateUp()
            }
                .addOnFailureListener {
                    Toast.makeText(view!!.context, it.toString(), Toast.LENGTH_SHORT).show()
                }
        }
        val ID = fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Todos").push().key!!.toString()
        val newTodoList = TodoList(ID,todoListName, todoItems)
        fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Todos/${ID}").setValue(newTodoList).addOnSuccessListener{
            newtodoItemsList.clear()
            todoItems.clear()
            _newTodoItemsLiveData.postValue(newtodoItemsList)
            Toast.makeText(view!!.context, R.string.new_todo_added, Toast.LENGTH_SHORT).show()
            view.findNavController().navigateUp()
        }.
        addOnFailureListener {
            Toast.makeText(view!!.context, it.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * Method to create new instance of Reminder and stores it in database
     * @param Name name of the Reminder
     * @param Desc description of the Reminder
     * @param Date Date of the Reminder
     * @param Time Time of the Reminder
     * @param view to display message and navigate back from form
     */
    fun saveNewReminder(view: View?, Name: String, Desc: String, Date: String, Time: String, reminder: reminder) {
        if (reminder.reminderID != null) {
            val newReminder = reminder(reminder.reminderID, Name, Desc, Date, Time)
            fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Reminders/${reminder.reminderID}")
                .setValue(newReminder).addOnSuccessListener {
                    _newTodoItemsLiveData.postValue(newtodoItemsList)
                    Toast.makeText(view!!.context, R.string.new_reminder_added, Toast.LENGTH_SHORT)
                    .show()
                    view.findNavController().navigateUp()
            }
                .addOnFailureListener {
                    Toast.makeText(view!!.context, it.toString(), Toast.LENGTH_SHORT).show()
                }
        }else {
            val ID =
                fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Reminders")
                    .push().key!!.toString()
            val newReminder = reminder(ID, Name, Desc, Date, Time)
            fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Reminders/${ID}")
                .setValue(newReminder).addOnSuccessListener {

                _newTodoItemsLiveData.postValue(newtodoItemsList)
                Toast.makeText(view!!.context, R.string.new_reminder_added, Toast.LENGTH_SHORT)
                    .show()
                view.findNavController().navigateUp()
            }
                .addOnFailureListener {
                    Toast.makeText(view!!.context, it.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun deleteNote(note: Note, view: View?) {
        fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Notes/${note.noteID}").removeValue().addOnSuccessListener {
            Toast.makeText(view!!.context, R.string.note_deleted, Toast.LENGTH_SHORT).show()
            view.findNavController().navigateUp()
        }.addOnFailureListener {
            Toast.makeText(view!!.context, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    fun deleteTodoList(todoList: TodoList, view: View?) {
        fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Todos/${todoList.todoListID}").removeValue().addOnSuccessListener {
            Toast.makeText(view!!.context, R.string.todolist_deleted, Toast.LENGTH_SHORT).show()
            view.findNavController().navigateUp()
        }.addOnFailureListener {
            Toast.makeText(view!!.context, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    fun deleteReminder(reminder: reminder, view: View?) {
        fireDatabase.getReference("${FirebaseAuthInstance.currentUser!!.uid}/Reminders/${reminder.reminderID}").removeValue().addOnSuccessListener {
            Toast.makeText(view!!.context, R.string.reminder_deleted, Toast.LENGTH_SHORT).show()
            view.findNavController().navigateUp()
        }.addOnFailureListener {
            Toast.makeText(view!!.context, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}

/**
 * function to map string of "true" to value of boolean true
 * or anything else to false
 * @return Boolean value of input
 */
private fun Any?.toBoolean(): Boolean {
    return if (this.toString().equals("true")) true else false
}
