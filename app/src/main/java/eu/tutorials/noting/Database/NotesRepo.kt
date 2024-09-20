package eu.tutorials.noting.Database

import androidx.lifecycle.LiveData
import eu.tutorials.noting.Models.Notes

class NotesRepo(private val NotesDao: NotesDao) {
    val allNotes:LiveData<List<Notes>> = NotesDao.getAllNotes()
    suspend fun insert (notes: Notes){
        NotesDao.insert(notes)
    }
    suspend fun delete(notes: Notes){
        NotesDao.delete(notes)
    }
    suspend fun update(notes: Notes){
        NotesDao.update(notes.id,notes.title,notes.body)
    }
}