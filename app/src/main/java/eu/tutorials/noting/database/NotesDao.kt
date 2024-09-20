package eu.tutorials.noting.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.tutorials.noting.models.Notes
@Dao
interface NotesDao {

    // Insert a note with conflict resolution strategy as REPLACE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Notes)

    // Delete a note
    @Delete
    suspend fun delete(note: Notes)

    // Query to get all notes ordered by ID in ascending order
    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Notes>>

    // Update a note with specific title and content based on the ID
    @Query("UPDATE notes_table SET title = :title, note = :note WHERE id = :id")
    suspend fun update(id: Int?, title: String?, note: String?): Int
}