package eu.tutorials.noting.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import eu.tutorials.noting.Models.Notes
import eu.tutorials.noting.R
import kotlin.random.Random

class NotesAdapter(private val context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    private val NotesList = ArrayList<Notes>()
    private val fullList = ArrayList<Notes>()

    private val generatedColors = mutableSetOf<Int>() // Store unique colors in a set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_items, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return NotesList.size
    }

    // Helper function to generate a random color
    private fun getRandomColor(): Int {
        val random = Random(System.currentTimeMillis())
        val r = random.nextInt(256)
        val g = random.nextInt(256)
        val b = random.nextInt(256)
        return (0xFF shl 24) or (r shl 16) or (g shl 8) or b // Return color as an ARGB Int value
    }

    // Function to generate a unique color
    private fun generateUniqueColor(): Int {
        var color: Int
        do {
            color = getRandomColor()
        } while (generatedColors.contains(color)) // Keep generating until you find a unique color

        generatedColors.add(color) // Add the new unique color to the set
        return color
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = NotesList[position]
        holder.title_text.text = currentNote.title
        holder.title_text.isSelected = true
        holder.note_text.text = currentNote.body
        holder.date_text.text = currentNote.date
        holder.date_text.isSelected = true

        // Assign a random unique color to each card's background
        holder.notes_layout.setCardBackgroundColor(generateUniqueColor())
        holder.notes_layout.cardElevation = 8f
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notes_layout = itemView.findViewById<CardView>(R.id.notes_card)
        val title_text = itemView.findViewById<TextView>(R.id.title)
        val note_text = itemView.findViewById<TextView>(R.id.note)
        val date_text = itemView.findViewById<TextView>(R.id.date)
    }
}
