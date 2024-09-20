package eu.tutorials.noting.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.tutorials.noting.Models.Notes

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (note: Notes)
    @Delete
    suspend fun delete(note: Notes)
    @Query("Select * from notes_table order by id ASC")
    fun getAllNotes(): LiveData<List<Notes>>
    @Query("UPDATE notes_table Set title = :title, note = :note WHERE id = :id")
    suspend fun update(id : Int?, title : String?, note : String?)

}