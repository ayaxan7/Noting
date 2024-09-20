package eu.tutorials.noting.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Notes(
    @PrimaryKey (autoGenerate = true) val id: Int?,
    @ColumnInfo (name = "title")val title:String?,
    @ColumnInfo(name="note")val body:String?,
    @ColumnInfo(name="data")val date:String?
)
