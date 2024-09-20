package eu.tutorials.noting.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import eu.tutorials.noting.Models.Notes
import eu.tutorials.noting.utilities.DATABASE_NAME

@Database(entities = arrayOf(Notes::class), version = 1, exportSchema = false)
abstract class NoteDB : RoomDatabase(){
    abstract fun getNoteDao() : NotesDao
    companion object{
        @Volatile
        private var Instance:NoteDB?=null
        fun getDatabase(context: Context):NoteDB{
            return Instance?: synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    NoteDB::class.java,
                    DATABASE_NAME
                ).build()
                Instance=instance
                instance
            }
        }
    }
}
