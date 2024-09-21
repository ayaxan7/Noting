package eu.tutorials.noting.adapter

import android.content.Context
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import eu.tutorials.noting.models.Notes
import eu.tutorials.noting.R
import kotlin.random.Random

class NotesAdapter(private val context: Context, val listener: NotesClickListener) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    private val NotesList = ArrayList<Notes>()
    private val fullList = ArrayList<Notes>()

    // Predefined colors from colors.xml
    private val noteColors = listOf(
        R.color.color1, R.color.color2, R.color.color3,
        R.color.color4, R.color.color5, R.color.color6
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_items, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return NotesList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = NotesList[position]
        holder.title_text.text = currentNote.title
        holder.note_text.text = currentNote.body
        holder.date_text.text = currentNote.date

        // Set a random height for each note card
        val randomHeight = Random.nextInt(250, 600) // Adjust the range as needed
        val layoutParams = holder.notes_layout.layoutParams
        layoutParams.height = randomHeight
        holder.notes_layout.layoutParams = layoutParams

        val colorResId = noteColors[position % noteColors.size]  // Cycles through the list
        holder.notes_layout.setCardBackgroundColor(ContextCompat.getColor(context, colorResId))

        holder.notes_layout.cardElevation = 15f
        holder.notes_layout.setOnClickListener {
            listener.onItemClicked(NotesList[holder.adapterPosition])
        }
        holder.notes_layout.setOnLongClickListener {
            listener.onLongItemClicked(NotesList[holder.adapterPosition], holder.notes_layout)
            true
        }
    }

    fun UpdateList(newList: List<Notes>) {
        fullList.clear()
        fullList.addAll(newList)
        NotesList.clear()
        NotesList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterList(search: String) {
        NotesList.clear()
        for (item in fullList) {
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.body?.lowercase()?.contains(search.lowercase()) == true) {
                NotesList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notes_layout = itemView.findViewById<CardView>(R.id.notes_card)
        val title_text = itemView.findViewById<TextView>(R.id.title)
        val note_text = itemView.findViewById<TextView>(R.id.note)
        val date_text = itemView.findViewById<TextView>(R.id.date)
    }

    interface NotesClickListener {
        fun onItemClicked(notes: Notes)
        fun onLongItemClicked(notes: Notes, cardView: CardView)
    }
}