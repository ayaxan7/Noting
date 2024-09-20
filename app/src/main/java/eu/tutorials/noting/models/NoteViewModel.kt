package eu.tutorials.noting.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import eu.tutorials.noting.database.NoteDB
import eu.tutorials.noting.database.NotesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val repository: NotesRepo
    val allNotes: LiveData<List<Notes>>
    init{
        val dao= NoteDB.getDatabase(application).getNoteDao()
        repository=NotesRepo(dao)
        allNotes=repository.allNotes
    }
    fun deleteNote(notes: Notes)=viewModelScope.launch(Dispatchers.IO){repository.delete(notes)}
    fun insertNote(notes: Notes)=viewModelScope.launch(Dispatchers.IO){repository.insert(notes)}
    fun updateNote(notes: Notes)=viewModelScope.launch(Dispatchers.IO){repository.update(notes)}
}
